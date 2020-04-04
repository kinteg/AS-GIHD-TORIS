insert into actions (id, name, action)
values (1, 'Loading', 'Загрузка')
ON CONFLICT DO NOTHING;

insert into actions (id, name, action)
values (2, 'Archiving', 'Архивация')
ON CONFLICT DO NOTHING;

insert into actions (id, name, action)
values (3, 'Unarchiving', 'Разархивация')
ON CONFLICT DO NOTHING;

insert into actions (id, name, action)
values (4, 'Adding', 'Добавление')
ON CONFLICT DO NOTHING;

insert into actions (id, name, action)
values (5, 'Update', 'Обновление')
ON CONFLICT DO NOTHING;