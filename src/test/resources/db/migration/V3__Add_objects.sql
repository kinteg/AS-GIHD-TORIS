insert into objects (id, name, object)
values (1, 'Source', 'Источник')
ON CONFLICT DO NOTHING;

insert into objects (id, name, object)
values (2, 'Pattern', 'Шаблон')
ON CONFLICT DO NOTHING;

insert into objects (id, name, object)
values (3, 'PatternTable', 'Таблица по шаблону')
ON CONFLICT DO NOTHING;