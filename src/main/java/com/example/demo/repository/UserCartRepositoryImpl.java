package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;
import com.example.demo.entity.UserCart;

@Transactional(propagation = Propagation.SUPPORTS)
public class UserCartRepositoryImpl implements UserCartCustom{
	@Autowired
    private EntityManager entityManager;
	
	@Override
	public boolean findCartForUser(long userId, long productId, short quantity) {
		Session session = entityManager.unwrap(Session.class);
		Optional<Integer> optional = session.createQuery("select 1 from UserCart u where u.userId = " + userId + " and u.productId = " + productId).uniqueResultOptional();
		
		if (optional.isPresent()) {
			Criteria crit = session.createCriteria(UserCart.class);
			crit.add(Restrictions.eq("userId",userId));
			crit.add(Restrictions.eq("productId",productId));
			UserCart result = (UserCart) crit.uniqueResult();
			result.setQuantity((short) (result.getQuantity() + quantity));
			
			session.merge(result);
			session.flush();
		} else {
			UserCart cart = new UserCart();
			cart.setProductId(productId);
			cart.setUserId(userId);
			cart.setQuantity(quantity);
			
			session.save(cart);
		}
		return false;
	}
	
	@Override
	public boolean isUserCartExist(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Optional<Integer> optional = session.createQuery("select 1 from UserCart u where u.userId = " + userId).uniqueResultOptional();
		
		return optional.isPresent();
	}

	@Override
	public List<UserCart> getProductsInCart(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UserCart.class);
		criteria.add(Restrictions.eq("userId", userId));
		
		return (List<UserCart>)criteria.list();
	}
}
