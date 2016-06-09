-- some raw SQL examples
create table u (
  id char (1) not null primary key ,
  name varchar,
);
create index fk_1_ind on u (id);

create table t(
  id int,
  id2 char(1),
  am int
);
create index fk_1_t_ind on t (id2);
alter table t add constraint fk_1 foreign key (id2) references u(id) on delete restrict on update cascade;

insert into u values ('A','Alfred');
insert into u values ('B','BAlfred');
insert into u values ('C','Carly');
insert into u values ('D','Darly');
insert into u values ('E','Erwin');

insert into t  values (1,'A',40);
insert into t  values (2,'A',41) ;
insert into t  values (3,'B',10);
insert into t  values (4,'C',50);
insert into t  values (5,'C',70);
insert into t  values (6,'B',40);
insert into t  values (7,'D',40);

alter table t drop constraint fk_1;
alter table t add  constraint fk_1 foreign key (id2) references u(id) on delete cascade on update cascade ;



-- insert into t values (8,'F',343);
update t set id2 = 'E' where  id2 = 'D';

select * from t;
select * from u;

delete from u where id = 'A';

select * from t;
select t.id, t.am , '||', u.name  from u left join t on u.id = t.id2 where t.id  is null;
select t.id, t.am , '||', u.name  from u join t on u.id = t.id2 ;


select a.id2 as t, max(a.su) from (select sum(am) AS su, id2 as id2 from t group by id2 ) as a
group by t order by 2 desc limit 1;

select top 1 a.id2, a.s from (select id2,sum(am) s from t group by id2 order by 2 desc ) as a;


SELECT
  id2,
  max(am)
FROM t
GROUP BY id2;


