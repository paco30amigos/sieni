evnt-click
                                                    futuro1-for de eventos del mismo componente
                                                    futuro2-for de relaciones --f_evento_nComponente_interacComp1
                                                    o1.evtn->click-f1_1,f2_1--f_click_1_1
                                                    subevtn->fade-f2_1,f3_1
                                                    subevtn->shake-f3_1,f1_2
                                                    o2.evtn->show-f1_2,f1_3
                                                    o3.evtn->click,f1_3
                                                    <c:set value="0" var="varID"></c:set>
                                                    <c:set value="" var="siguienteEvento"></c:set>

                                                    <c:forEach items="#{superComponente.interacciones}" var="acciones">
                                                        <c:set value="#{varID+1}" var="varID"></c:set>
                                                        <c:choose>
                                                            <c:when test="#{varID>=superComponente.interacciones.size()}">
                                                                <c:set value="" var="siguienteEvento"></c:set>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set value=",func#{varID+1}" var="siguienteEvento"></c:set>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="#{acciones.idEvento.evCodigo=='show' and varID==1}">
                                                                <script class="scripGen" type="text/javascript">
                                                                    function func#{varID}() {
                                                                        mediaEv = $(".multimedia");
                                                                        mediaEv.show("#{acciones.idAccion.evCodigo}", #{acciones.inDuracion}#{siguienteEvento});
                                                                    }
                                                                </script>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <script class="scripGen" type="text/javascript">
                                                                    function func#{varID}() {
                                                                        mediaEv = $(".multimedia");//id de super componente actual
                                                                        mediaEv.effect("#{acciones.idAccion.evCodigo}", #{acciones.inDuracion}#{siguienteEvento});
                                                                    }
                                                                </script>
                                                            </c:otherwise>
                                                        </c:choose>                                    
                                                    </c:forEach>-->
