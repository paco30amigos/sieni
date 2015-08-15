///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sv.com.mined.sieni.viejo;
//
//import javax.persistence.EntityManager;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Query;
//import sv.com.mined.sieni.model.SieniAlumno;
//import sv.com.mined.sieni.pojos.PagedResult;
//
///**
// *
// * @author francisco_medina
// */
//public class paginator {
////http://antoniogoncalves.org/2012/05/24/how-to-get-the-jpqlsql-string-from-a-criteriaquery-in-jpa/
//
//    EntityManager em;
//
//    PagedResult<Object> paginate(String tipoQuery,
//            Query q,
//            PagedResult<Object> a) {
//        tipoQuery = "namedQuery";
//        SieniAlumno aja = null;
//
//        StringBuffer namedQuery = new StringBuffer();
////        namedQuery.append(getNamedQueryCode(aja.getClass(), query));
//
//        int pageNumber = a.getPageNumber();
//        int pageSize = a.getPageSize();
//        q.setFirstResult((pageNumber - 1) * pageSize);
//        q.setMaxResults(pageSize);
//        a.setList(q.getResultList());
//        return a;
//    }
//
////    private PagedResult<Object> paginateNamedQuery() {
////
////    }
//
//    public void prueba() {
//        Query query = null;
//        PagedResult<SieniAlumno> a;
////        paginate(query, null);
//    }
//
//    String getNamedQueryCode(Class<? extends Object> clazz, String namedQueryKey) {
//        NamedQueries namedQueriesAnnotation = clazz.getAnnotation(NamedQueries.class);
//        NamedQuery[] namedQueryAnnotations = namedQueriesAnnotation.value();
//
//        String code = null;
//        for (NamedQuery namedQuery : namedQueryAnnotations) {
//            if (namedQuery.name().equals(namedQueryKey)) {
//                code = namedQuery.query();
//                break;
//            }
//        }
//
//        if (code == null) {
//            if (clazz.getSuperclass().getAnnotation(MappedSuperclass.class) != null) {
//                code = getNamedQueryCode(clazz.getSuperclass(), namedQueryKey);
//            }
//        }
//
//        //if not found
//        return code;
//    }
//}
