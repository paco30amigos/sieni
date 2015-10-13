/*insert into sieni_accion(id_accion,ac_descripcion,ev_codigo,id_tipo_super_compon)
(select id_accion+8,ac_descripcion,ev_codigo,7 from sieni_accion where id_accion>=45);*/

/*insert into sieni_evento(id_evento,ev_descripcion,ev_codigo,id_tipo_super_compon)
(select row_number() over (order by id_evento)+26,ev_descripcion,ev_codigo,7 from sieni_evento where id_tipo_super_compon=2 order by id_evento);*/

DELETE FROM sieni_super_compon
 WHERE sc_estado='I'

select * from sieni_super_compon sc where sc_estado='I';

select * from sieni_componente c ,sieni_super_compon sc where sc_estado='I' and sc.id_super_compon=c.id_super_compon;
select * from sieni_comp_interaccion ci,sieni_componente c ,sieni_super_compon sc where sc_estado='I' and sc.id_super_compon=c.id_super_compon and ci.id_componente=c.id_componente;

delete from sieni_comp_interaccion where id_comp_interaccion in (select id_comp_interaccion from sieni_comp_interaccion ci,sieni_componente c ,sieni_super_compon sc where sc_estado='I' and sc.id_super_compon=c.id_super_compon and ci.id_componente=c.id_componente);
delete from sieni_componente where id_componente in (select id_componente from sieni_componente c ,sieni_super_compon sc where sc_estado='I' and sc.id_super_compon=c.id_super_compon);