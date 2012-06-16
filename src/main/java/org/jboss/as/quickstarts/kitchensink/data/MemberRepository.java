/* 
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.as.quickstarts.kitchensink.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.jboss.as.quickstarts.kitchensink.model.Member;


@ApplicationScoped
public class MemberRepository {

   @Inject
   private FullTextEntityManager em;

   public Member findById(Long id) {
      return em.find(Member.class, id);
   }

   public Member findByEmail(String email) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
      Root<Member> member = criteria.from(Member.class);
      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
      // feature in JPA 2.0
      // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
      criteria.select(member).where(cb.equal(member.get("email"), email));
      return em.createQuery(criteria).getSingleResult();
   }

   public List<Member> findAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
      Root<Member> member = criteria.from(Member.class);
      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
      // feature in JPA 2.0
      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
      criteria.select(member).orderBy(cb.asc(member.get("name")));
      return em.createQuery(criteria).getResultList();
   }


// public void checkInfinispanEntityCache() {
// SessionFactory factory = ( (OgmEntityManagerFactory) em.getEntityManagerFactory() ).getSessionFactory();
// final SessionFactoryObserver observer = ( (SessionFactoryImplementor) factory ).getFactoryObserver();
// if ( observer == null ) {
//     throw new RuntimeException( "Wrong OGM configuration: observer not set" );
// }
// Cache cache = ( (GridMetadataManager) observer ).getCacheContainer().getCache( "ENTITIES" );
//
// cacheEntries = new ArrayList<CacheEntry>();
// for ( Object o : cache.entrySet() ) {
//     Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
//     CacheEntry keyValue = new CacheEntry( entry.getKey().toString(), (Map<String, ?>) entry.getValue() );
//     cacheEntries.add( keyValue );
// }
//}
//
//public static class CacheEntry {
// private String key;
// private String value;
//
// CacheEntry(String key, Map<String, ?> valueMap) {
//     this.key = key;
//     this.value = createStringValue( valueMap );
// }
//
// public String getKey() {
//     return key;
// }
//
// public String getValue() {
//     return value;
// }
//
// private String createStringValue(Map<String, ?> valueMap) {
//     StringBuilder builder = new StringBuilder();
//     builder.append( "Value{" );
//     for ( Map.Entry<String, ?> valueEntry : valueMap.entrySet() ) {
//         if ( !builder.toString().endsWith( "Value{" ) ) {
//             builder.append( ",\n" );
//         }
//         builder.append( valueEntry.getKey() );
//         builder.append( "=" );
//         builder.append( valueEntry.getValue().toString() );
//     }
//     builder.append( "}" );
//     return builder.toString();
// }
//}
}