/** ** TORIS Widget API  ****/
import simpleStorage from 'simplestorage.js'
import html2canvas from 'html2canvas'

window.TORIS = {
  sys_id: false, // id подключаемой системы
  domain: '', // домен, с которого необходимо загружать виджет
  is_toris: true, // система является частью ТОРИС (по умолчанию true)
  support: {
    email: false, // email ТП для отправки заявок
    faq_link: false, // Ссылка на FAQ по системе
    help_link: false, // Ссылка на справку по системе
    feedback_link: false // Ссылка на форму обратной связи с тех поддержкой
  },

  show_auth: true, // Показывать модалку авторизации, если пользователь не авторизован
  redirect_to_auth: false, // Сразу переходить на страницу входа

  menu: [], // Конфигурация меню
  login_redirect_url: false, // URL для редиректа после успешного входа
  logout_redirect_url: false, // URL для редиректа после успешного выхода

  domain_proto: '', // Явное указание протокола, с которого загружать виджет (для работы http - https)

  end_session_event_redirect: false, // URL для редиректа в случае завершения сессии

  own_login: { // Параметры собственной формы авторизации
    isset: false, // Есть ли собственная форма входа
    url: '' // Адрес собственной формы входа
  },

  css_path: false, // Путь до css для панели

  authFormUrl: '/widget/?login=yes', // Адрес формы входа ЕСОВ

  wFrame: false,
  wFrameLoaded: false,

  cache_time: {
    profile: 2 * 60 * 60 * 1000 // Кешируем профиль на 2 часа
  },

  console: {
    debug: true,
    prefix: '[TORIS.WIDGET.SYS_SIDE] [' + new Date() + ']'
  },

  _info () {
    if (window.TORIS.console.debug) {
      console.info(window.TORIS.console.prefix, ...arguments)
    }
  },
  _log () {
    if (window.TORIS.console.debug) {
      console.log(window.TORIS.console.prefix, ...arguments)
    }
  },

  /** * Установка параметров виджета ***/
  setOptions: function (options) {
    window.TORIS._info('Установка параметров виджета:', options)

    for (const key of [
      'sys_id',
      'domain',
      'menu',
      'css_path',
      'login_redirect_url',
      'logout_redirect_url',
      'domain_proto',
      'end_session_event_redirect'
    ].filter(x => options.propertyIsEnumerable(x))) {
      window.TORIS[key] = options[key]
    }
    for (const key of [
      'is_toris',
      'show_auth',
      'redirect_to_auth'
    ].filter(x => options.propertyIsEnumerable(x))) {
      window.TORIS[key] = !!options[key]
    }

    for (const root of ['support', 'own_login']) {
      for (const field of Object.entries(window.TORIS[root]).filter(x => options[root] && options[root][x])) {
        window.TORIS[root][field] = options[root][field]
      }
    }

    window.TORIS.domain_proto = window.TORIS.domain_proto || window.location.protocol
    window.TORIS.authFormUrl = window.TORIS.domain_proto + '//' + window.TORIS.domain + window.TORIS.authFormUrl
  },

  /** * Подключение js скриптов ***/
  include: function (path) {
    return new Promise(function (resolve) {
      const script = document.createElement('script')
      script.type = 'text/javascript'

      if (script.readyState) { // IE
        script.onreadystatechange = function () {
          if (script.readyState === 'loaded' || script.readyState === 'complete') {
            script.onreadystatechange = null
            resolve()
          }
        }
      } else { // Others
        script.onload = function () {
          resolve()
        }
      }

      script.src = path
      document.getElementsByTagName('head')[0].appendChild(script)
    })
  },

  /** * Инициализация виджета ***/
  init: function (callback) {
    /**
     * Проверка на наличие родительского фрейма
     * Отображаем виджет только если запускаем без встраивания во фрейм
     */
    window.TORIS._info('domain: ', window.TORIS.domain)
    window.TORIS._info('sys_id: ', window.TORIS.sys_id)

    window.TORIS._createWidget(function () {
      // Если не указан id системы или не передан домен портала, возвращаем ошибку
      if (!window.TORIS.sys_id || !window.TORIS.domain) {
        // Показываем модалку с сообщением о некорректности id системы
        window.TORIS._sendMessageToWidget('init_error')

        if (callback && typeof callback === 'function') {
          callback(Error('Некоректные параметры инициализации виджета'))
        }
      } else {
        window.TORIS._sendEnterEvent()
        // Workaround for The structured clone algorithm (https://developer.mozilla.org/en-US/docs/Web/API/Web_Workers_API/Structured_clone_algorithm)
        window.TORIS._sendMessageToWidget('init', JSON.parse(JSON.stringify(window.TORIS)))

        if (callback && typeof (callback) === 'function') {
          callback(undefined, 'Виджет успешно инициализирован. Запущен процесс отображения панели')
        }
      }
    })
  },

  /***
   * Аудит действий пользователя
   *
   * Возможные типы событий:
   * login - аутентификация (фиксируется автоматически)
   * logout - выход из аутентификации (фиксируется автоматически)
   * link - переход по ссылке (фиксируется автоматически, по возможности)
   * enter - вход в систему (фиксируется автоматически)

   * print - формирование печатной формы
   * report - формирование отчета
   * export - экспорт данных
   * createItem - создание объекта
   * changeItem - изменение объекта
   * removeItem - удаление объекта
   * callService - вызов внешнего сервиса
   * mapCall - обращение к карте (автоматически фиксируется из ГеоПС)
   *
   * Дополнительно к событию аудита передается сериализованный массив параметров params (для каждого метода свой набор).
   * Для сериализации применяется метод JSON.stringify()
   *
   ***/
  audit: function (event, object) {
    window.TORIS._info('Audit event:', event)

    fetch(window.TORIS.domain_proto + '//' + window.TORIS.domain + '/api/monitoring/audit/events/', {
      method: 'POST',
      body: JSON.stringify({
        event,
        object
      }),
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'SystemID': window.TORIS.sys_id
      }
    }).then(function (response) {
      response.json().then(data => {
        window.TORIS._info('audit result: ', data)
      })
    })
  },

  /** * Получение профиля текущего профиля ***/
  userProfile: function (callback) {
    // Отправляем запрос на получение профиля
    fetch(window.TORIS.domain_proto + '//' + window.TORIS.domain + '/api/personal/user/profile/', {
      credentials: 'include',
      headers: {
        'SystemID': window.TORIS.sys_id
      }
    }).then(response => {
      response.json().then(data => {
        if (!response.ok) {
          window.TORIS._log(data)
          window.TORIS._log('window.TORIS.show_auth:', window.TORIS.show_auth)
          window.TORIS._log('window.TORIS.redirect_to_auth:', window.TORIS.redirect_to_auth)
          if (window.TORIS.show_auth && (+data.code === 103 || +data.code === 104 || +data.code === 106)) {
            window.TORIS._log('Показываем окно авторизации')
            // Показываем окно необходимости авторизации
            window.TORIS._sendMessageToWidget('showAuthWindow')
          }
          if (window.TORIS.redirect_to_auth && (+data.code === 103 || +data.code === 106)) {
            window.TORIS._log('Переходим на страницу входа')
            document.location.href = window.TORIS.domain_proto + '//' + window.TORIS.domain + '?login=yes' + '&back_url=' + window.TORIS.login_redirect_url
          }
        }

        callback(data)
      })
    })
  },

  /** * Синхронный метод проверки валидности сессии и получения aistoken ***/
  getSyncProfile: function () {
    return new Promise((resolve, reject) => {
      fetch(window.TORIS.domain_proto + '//' + window.TORIS.domain + '/api/personal/user/profile/', {
        credentials: 'include',
        headers: {
          'SystemID': window.TORIS.sys_id
        }
      }).then(function (response) {
        response.json().then(data => {
          if (response.ok) {
            resolve(data)
          } else {
            reject(data)
          }
        })
      })
    })
  },

  /** * Отправка системных уведомлений пользователям ***/
  notifier: function (message, link, userESOVid, callback) {
    window.TORIS._info('Send message to user:', message)

    fetch(window.TORIS.domain_proto + '//' + window.TORIS.domain + '/api/notifier/', {
      method: 'POST',
      body: JSON.stringify({
        message,
        link,
        userESOVid
      }),
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'SystemID': window.TORIS.sys_id
      }
    }).then(function (response) {
      response.json().then(data => {
        if (callback && typeof (callback) === 'function') {
          callback(data)
        }
      })
    })
  },

  /***
   *
   * Отправка сообщений на email
   *
   * Поддерживает пакетную отправку
   * В messages массив:
   * mailTo, title, body (может быть html)
   *
   ***/
  sendEmail: function (messages, callback) {
    window.TORIS._info('Send message to email:', messages)

    fetch(window.TORIS.domain_proto + '//' + window.TORIS.domain + '/api/email/', {
      method: 'POST',
      body: JSON.stringify({
        messages,
        sys_id: window.TORIS.sys_id
      }),
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'SystemID': window.TORIS.sys_id
      }
    }).then(function (response) {
      if (callback && typeof (callback) === 'function') {
        response.json().then(callback)
      }
    })
  },

  /** * Отправка кроссдоменного сообщения в виджет ***/
  _sendMessageToWidget: function (command, message) {
    message = message || {}
    message.command = command
    window.TORIS._info('Отправляем кроссдоменный запрос: ', message)

    if (!window.TORIS.wFrameLoaded) {
      window.TORIS.wFrame.onload = function () {
        window.TORIS.wFrameLoaded = true
        const content = this.contentWindow // Bypass PostMessageCheck
        content.postMessage(message, '*')
      }
    } else {
      const content = window.TORIS.wFrame.contentWindow // Bypass PostMessageCheck
      content.postMessage(message, '*')
    }
  },

  _sendEnterEvent: function () {
    // Фиксируем вход в систему
    // Вход - это заход в систему за последние 15 минут (визит)
    if (window.TORIS.sys_id) {
      if (!simpleStorage.get('userEnter_' + window.TORIS.sys_id)) {
        window.TORIS.audit('enter')
        simpleStorage.set('userEnter_' + window.TORIS.sys_id, 1, { TTL: 15 * 60 * 1000 })
      } else {
        // Если пользователь входил  менее, чем 5 секунд назад, не считаем входом и продлеваем сессию
        simpleStorage.setTTL('userEnter_' + window.TORIS.sys_id, 15 * 60 * 1000)
        window.TORIS._info('Already logged in')
      }
    } else {
      window.TORIS._info('Не определен код системы')
    }
  },

  /** * Создание виджета панели ***/
  _createWidget: function (callback) {
    const sourceDomain = (window.TORIS.domain_proto ? window.TORIS.domain_proto : document.location.protocol) + '//' + window.TORIS.domain

    // Передаем во фрейм ссылку на текущую страницу, чтобы работали кроссдоменные запросы
    let widgetUrl = sourceDomain + '/widget/'
    widgetUrl += '?systemID=' + this.sys_id
    if (this.css_path) {
      widgetUrl += '&css_path=' + this.css_path
    }
    widgetUrl += '&_=' + Date.now()
    widgetUrl += '#' + encodeURIComponent(document.location.href)

    const Widget = {
      wHeight: '42px', // TODO: Подумать, может можно автоматом получать
      created: false,
      widgetElement: null,
      show: function () {
        if (this.created) {
          return
        }
        this.widgetElement = document.createElement('div')
        this.widgetElement.setAttribute('id', 'widget_container')
        this.widgetElement.innerHTML = '<iframe id="widget_iframe" src="' + widgetUrl +
          '" scrolling="no" width="100%" frameborder="0" style="top: 0; left: 0; position: fixed; z-index:10000"></iframe>'

        document.body.insertBefore(this.widgetElement, document.body.firstChild)
        this.widgetElement.style.display = 'block'
        this.created = true
        window.TORIS.wFrame = document.getElementById('widget_iframe')
        window.TORIS.wFrame.height = this.wHeight
      }
    }

    // Меняем стили body, чтобы корректно панель отображалась сверху
    // var body = document.querySelector("body");
    document.body.style.position = 'static'
    document.body.style.marginTop = Widget.wHeight

    // Ловим кроссдоменные запросы
    window.addEventListener('message', function (e) {
      if (e.origin === sourceDomain) {
        window.TORIS._crossdomainListeners(e)
      }
    })

    Widget.show()

    return callback()
  },

  /** * Отработка кроссдоменных запросов из внутреннего скрипта виджета ***/
  _crossdomainListeners: function (message) {
    if (message.data !== undefined && window.TORIS.wFrame) {
      window.TORIS._log('receive xd message: ', message.data)

      switch (message.data.command) {
        // Меняем размер фрейма либо под высоту панели, либо под требуемую
        case 'resize':
          // Костыль т.к. в firefox панелька съеживается после отправки сообщения через форму "Сообщить о проблеме"
          if (Number.isInteger(message.data.height)) {
            message.data.height = message.data.height + 'px'
          }

          document.getElementById('widget_iframe').style.height = message.data.height
          break

        // Ставим максимальную высоту фрейма для модалок
        case 'fullHeight':
          document.getElementById('widget_iframe').style.height = '100%'
          break

        // Делаем скрип и отправляем баг репорт
        case 'sendBugReport':
          const sendReport = function (image) {
            window.TORIS._sendBugReportToServer(window.location.href, image, message.data.report)
          }
          // Если прилага на флексе, то делаем скрин через встроенную в нее функцию
          if (document.toris_operator) {
            window.TORIS._info('Скриним флеш')
            sendReport(document.toris_operator.getScreenshot())
          } else {
            window.TORIS._info('Скриним canvas')

            // Иначе делаем обычный скрин через канвас
            html2canvas(document.body, {
              onrendered: function (canvas) {
                sendReport(canvas.toDataURL('image/png')) // Сохраняем скрин в файл
              }
            })
          }

          break

        // Вызван пункт общего меню
        case 'commonMenuItemCall':
          // Вызываем событие
          let commonMenuItemCall = document.createEvent('CustomEvent')
          commonMenuItemCall.initCustomEvent('commonMenuItemCall', false, false, message.data.item)
          window.dispatchEvent(commonMenuItemCall)
          break

        // Окончание успешной инициализации виджета
        case 'TORISWidgetInitComplete':
          let TORISWidgetInitComplete = document.createEvent('CustomEvent')
          TORISWidgetInitComplete.initCustomEvent('TORISWidgetInitComplete', false, false, {})
          window.dispatchEvent(TORISWidgetInitComplete)
          break

        // Переход по ссылке из фрейма
        case 'redirectToUrl':
          if (message.data.url.length > 0) {
            document.location.href = message.data.url
          }
          break
      }
    }
  },

  _sendBugReportToServer: function (url, image, message) {
    // Отправляем баг репорт
    fetch(window.TORIS.domain_proto + '//' + window.TORIS.domain + '/local/ajax/support/bugReport/', {
      method: 'POST',
      body: JSON.stringify({
        url,
        message,
        system: {
          id: window.TORIS.sys_id
        },
        is_toris: window.TORIS.is_toris,
        support_email: window.TORIS.support.email,
        imgBase64: image
      }),
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'SystemID': window.TORIS.sys_id
      }
    }).then(function (response) {
      response.json().then(data => {
        window.TORIS._info('Send bug report: ', data)
      })
    })
  }
}
