insert into errors (id, name, error, error_status)
values (1, 'Nothing', '-', '')
ON CONFLICT DO NOTHING;

insert into errors (id, name, error, error_status)
values (2, 'Validation', 'Ошибка валидации', '-1')
ON CONFLICT DO NOTHING;

insert into errors (id, name, error, error_status)
values (3, 'Exist', 'Уже существует', '-2')
ON CONFLICT DO NOTHING;

insert into errors (id, name, error, error_status)
values (4, 'No exist', 'Не существует', '-3')
ON CONFLICT DO NOTHING;

insert into errors (id, name, error, error_status)
values (5, 'Null data', 'Необходимые данные не существуют', '-4')
ON CONFLICT DO NOTHING;

insert into errors (id, name, error, error_status)
values (6, 'Null', 'Ошибка базы данных', '-5')
ON CONFLICT DO NOTHING;