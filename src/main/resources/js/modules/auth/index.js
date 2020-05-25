import './widget'
import { setToken } from './cookies'

export * from './cookies'

export function defaultConfig (store, router, error, success) {
  const onLogout = () => {
    error('Ошибка верификации, пожалуйста попробуйте ещё раз.')
    window.location = store.state.config.TORIS_DOMAIN + '/?login=yes&back_url=' + window.location.origin
  }
  const origin = window.location.origin
  return {
    config: {
      sys_id: store.state.config.TORIS_CODE,
      domain: store.state.config.TORIS_DNS,
      css_path: origin + '/toris/widget.css',
      panelWidth: 40
    },
    error: () => {
      store.dispatch('user/FedLogout').then(onLogout)
    },
    success: data => {
      setToken(data.AISTOKEN)
      store.commit('user/SET_TOKEN', data.AISTOKEN)
      store.dispatch('user/UpdateUserInfo').then(response => {
        if (response.ok) {
          if (router.currentRoute.name === 'denied') {
            router.push({ name: 'home' })
          }
          success()
        } else {
          response.json().then(data => {
            if (data.code === 401) {
              if (router.currentRoute.name !== 'denied') {
                router.push({ name: 'denied' })
              }
            } else {
              store.dispatch('user/FedLogout').then(onLogout)
            }
          })
        }
      })
    }
  }
}

export function torisInit ({ config, success, error }) {
  window.TORIS.setOptions(config);

  window.addEventListener('TORISWidgetInitComplete', () =>
      window.TORIS.userProfile(result => {
        window.TORIS._info('Профиль пользователя:', result);
        if (result && result.data) {
          success(result.data)
        } else if (result.code === 103) {
          error(result)
        } else {
          success({ AISTOKEN: null })
        }
      })
  )

  window.TORIS.init((error, message) => {
    if (config.panelWidth && typeof config.panelWidth === 'number' && config.panelWidth > 0 && config.panelWidth < 100) {
      const element = document.getElementById('widget_iframe')
      element.style.left = 'auto';
      // Отслеживаем изменения высоты панели, чтобы менять ширину
      (new MutationObserver(mutations => {
        mutations.forEach(() => {
          if (element.style.height === '100%' && element.style.width !== '100%') {
            element.style.width = '100%'
          } else if (element.style.height !== '100%' && element.style.width === '100%') {
            element.style.width = config.panelWidth + '%'
          }
        })
      })).observe(element, { attributes: true, attributeFilter: ['style'] })
    }
    window.TORIS._info('Результат первичной инициализации:', error || message)
  })
}
