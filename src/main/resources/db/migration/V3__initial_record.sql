INSERT INTO tags (id, name, description) VALUES
  (1, "Java", "Java基礎知識"),
  (2, "Spring Core", "Springフレームワークの基本"),
  (3, "中継層", "Configurationクラスなどの使用"),
  (4, "Application層", "Controllerクラス又はHelperクラスの使用"),
  (5, "Domain層", "Serviceクラス又はDomainObjectクラスの使用"),
  (6, "Infrastructure層", "Repositoryクラス、Mapperクラス又はEntityクラスの使用");

-- Java basics (tag_id 1)
INSERT INTO links (created_at, updated_at, description, name, url, tag_id) VALUES
(NOW(), NOW(), 'Official Java documentation', 'Java Docs', 'https://docs.oracle.com/javase/', 1),
(NOW(), NOW(), 'Java tutorials by Oracle', 'Java Tutorials', 'https://docs.oracle.com/javase/tutorial/', 1),
(NOW(), NOW(), 'Java programming exercises', 'Java Exercises', 'https://www.w3schools.com/java/java_exercises.asp', 1);

-- Spring Framework (tag_id 2)
INSERT INTO links (created_at, updated_at, description, name, url, tag_id) VALUES
(NOW(), NOW(), 'Official Spring Framework documentation', 'Spring Docs', 'https://spring.io/projects/spring-framework', 2),
(NOW(), NOW(), 'Spring Framework tutorials', 'Spring Guides', 'https://spring.io/guides', 2),
(NOW(), NOW(), 'Spring Framework reference', 'Spring Reference', 'https://docs.spring.io/spring-framework/reference/', 2);

-- Configuration (tag_id 3)
INSERT INTO links (created_at, updated_at, description, name, url, tag_id) VALUES
(NOW(), NOW(), 'Spring Configuration tutorial', 'Spring Configuration', 'https://www.baeldung.com/spring-configuration', 3),
(NOW(), NOW(), 'Java Configuration vs XML', 'Java Config', 'https://www.baeldung.com/spring-java-config', 3);

-- Application layer (tag_id 4)
INSERT INTO links (created_at, updated_at, description, name, url, tag_id) VALUES
(NOW(), NOW(), 'Spring MVC tutorial', 'Spring MVC', 'https://spring.io/guides/gs/serving-web-content/', 4),
(NOW(), NOW(), 'REST Controllers guide', 'Spring REST', 'https://spring.io/guides/tutorials/rest/', 4);

-- Domain layer (tag_id 5)
INSERT INTO links (created_at, updated_at, description, name, url, tag_id) VALUES
(NOW(), NOW(), 'Domain-Driven Design basics', 'DDD Basics', 'https://martinfowler.com/bliki/DomainDrivenDesign.html', 5),
(NOW(), NOW(), 'Service layer pattern', 'Service Layer', 'https://martinfowler.com/eaaCatalog/serviceLayer.html', 5);

-- Infrastructure layer (tag_id 6)
INSERT INTO links (created_at, updated_at, description, name, url, tag_id) VALUES
(NOW(), NOW(), 'Spring Data JPA documentation', 'Spring Data', 'https://spring.io/projects/spring-data-jpa', 6),
(NOW(), NOW(), 'Hibernate ORM guide', 'Hibernate', 'https://hibernate.org/orm/documentation/', 6),
(NOW(), NOW(), 'MyBatis documentation', 'MyBatis', 'https://mybatis.org/mybatis-3/', 6);