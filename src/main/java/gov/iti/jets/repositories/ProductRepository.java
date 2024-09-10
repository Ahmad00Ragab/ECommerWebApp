package gov.iti.jets.repositories;

import gov.iti.jets.dtos.ProductDto;
import gov.iti.jets.genericDao.GenericDaoImpl;
import gov.iti.jets.models.Category;
import gov.iti.jets.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProductRepository extends GenericDaoImpl<Product>{

    public ProductRepository() {
        super(Product.class);
    }


    public Optional<Product> getProductByName(String name) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> q = cb.createQuery(Product.class);
            Root<Product> productRoot = q.from(Product.class);

            q.select(productRoot).where(cb.equal(productRoot.get("name"), name));

            return Optional.of(em.createQuery(q).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Set<Product> findProductsByCategory(String category) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String fetchingByCategory = "FROM Product p JOIN p.category c WHERE c.name = :category";
            Query query = em.createQuery(fetchingByCategory);
            query.setParameter("category", category);
            return new HashSet<>(query.getResultList());
        }finally{
            if (em != null) {
                em.close();
            }
        }
    }

    public Set<Product> findProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String fetchingByPrice = "SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice";
            Query query = em.createQuery(fetchingByPrice);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Set<Product> sortProductsByPrice() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String sortByPriceQuery = "SELECT p FROM Product p ORDER BY p.price ASC";
            Query query = em.createQuery(sortByPriceQuery);
            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public Set<Product> sortProductsByCategoryAndPrice(String category) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String sortProductCat = "SELECT p FROM Product p JOIN p.category c WHERE c.name = :category ORDER BY p.price ASC";
            TypedQuery<Product> query = em.createQuery(sortProductCat, Product.class);
            query.setParameter("category", category);
            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Set<ProductDto> findAllProductsUsingDTO(int pageNumber, int pageSize) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductDto> cq = cb.createQuery(ProductDto.class);
            Root<Product> productRoot = cq.from(Product.class);

            cq.select(cb.construct(ProductDto.class,
                    productRoot.get("id"),
                    productRoot.get("name"),
                    productRoot.get("description"),
                    productRoot.get("imageUrl"),
                    productRoot.get("price")
            ));

            TypedQuery<ProductDto> query = em.createQuery(cq);

            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    // criteria query converted
    public Set<ProductDto> findProductsByCategoryUsingProductDTO(String category, int pageNumber, int pageSize) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductDto> cq = cb.createQuery(ProductDto.class);
            Root<Product> productRoot = cq.from(Product.class);


            Join<Product, Category> categoryJoin = productRoot.join("category");


            cq.select(cb.construct(ProductDto.class,
                    productRoot.get("id"),
                    productRoot.get("name"),
                    productRoot.get("description"),
                    productRoot.get("imageUrl"),
                    productRoot.get("price")));

            cq.where(cb.equal(categoryJoin.get("name"), category));

            TypedQuery<ProductDto> query = em.createQuery(cq);

            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }



    public Set<ProductDto> findProductByNameUsingProductDTO(String name, int pageNumber, int pageSize) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductDto> cq = cb.createQuery(ProductDto.class);
            Root<Product> productRoot = cq.from(Product.class);

            cq.select(cb.construct(ProductDto.class,
                    productRoot.get("id"),
                    productRoot.get("name"),
                    productRoot.get("description"),
                    productRoot.get("imageUrl"),
                    productRoot.get("price")
            ));

            cq.where(cb.like(cb.lower(productRoot.get("name")), "%" + name.toLowerCase() + "%"));

            TypedQuery<ProductDto> query = em.createQuery(cq);

            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public Set<Product> sortProductsByCategoryAndPriceUsingProductDTO(String category) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String sortProductCat = "SELECT " +
                    "new gov.iti.jets.dtos.ProductDto(p.id,p.name,p.description,p.imageUrl,p.price) " +
                    "FROM Product p JOIN p.category c WHERE c.name = :category ORDER BY p.price ASC";
            TypedQuery<Product> query = em.createQuery(sortProductCat, Product.class);
            query.setParameter("category", category);
            return new HashSet<>(query.getResultList());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    ///  counting products processes



    public int countProductsByName(String name) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Product> productRoot = cq.from(Product.class);

            cq.select(cb.count(productRoot));
            cq.where(cb.like(cb.lower(productRoot.get("name")), "%" + name.toLowerCase() + "%"));

            TypedQuery<Long> query = em.createQuery(cq);
            return query.getSingleResult().intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int countProductsByCategory(String category) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Product> productRoot = cq.from(Product.class);

            Join<Product, Category> categoryJoin = productRoot.join("category");
            cq.select(cb.count(productRoot)).where(cb.equal(categoryJoin.get("name"), category));

            return em.createQuery(cq).getSingleResult().intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public int countAllProducts() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Product> productRoot = cq.from(Product.class);

            cq.select(cb.count(productRoot));

            return em.createQuery(cq).getSingleResult().intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }





}
