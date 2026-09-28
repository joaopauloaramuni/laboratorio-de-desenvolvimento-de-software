-- Rode no Supabase > SQL Editor (opcional: o Hibernate cria a tabela com ddl-auto=update)
create table if not exists public.favorites (
    station_uuid varchar(64) primary key,
    created_at   timestamptz not null default now()
);

-- A app conecta direto no Postgres (usuário postgres), que ignora RLS.
-- Habilitar RLS sem policies bloqueia o acesso público via API REST/anon key.
alter table public.favorites enable row level security;
