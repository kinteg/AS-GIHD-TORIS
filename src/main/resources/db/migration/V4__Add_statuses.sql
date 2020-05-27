insert into statuses (id, name, status)
values (1, 'Loading', 'Загружается')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (2, 'Loaded', 'Загружен')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (3, 'Archived', 'Архивирован')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (4, 'Unarchived', 'Разархивирован')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (5, 'Added', 'Добавлен')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (6, 'Uploaded', 'Обновлён')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (7, 'No Loaded', 'Не Загружен')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (8, 'No Archived', 'Не Архивирован')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (9, 'No Unarchived', 'Не Разархивирован')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (10, 'No Added', 'Не Добавлен')
ON CONFLICT DO NOTHING;

insert into statuses (id, name, status)
values (11, 'No Uploaded', 'Не Обновлён')
ON CONFLICT DO NOTHING;