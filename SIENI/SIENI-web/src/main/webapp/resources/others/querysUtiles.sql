/*insert into sieni_accion(id_accion,ac_descripcion,ev_codigo,id_tipo_super_compon)
(select id_accion+8,ac_descripcion,ev_codigo,7 from sieni_accion where id_accion>=45);*/

/*insert into sieni_evento(id_evento,ev_descripcion,ev_codigo,id_tipo_super_compon)
(select row_number() over (order by id_evento)+26,ev_descripcion,ev_codigo,7 from sieni_evento where id_tipo_super_compon=2 order by id_evento);*/