package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.DriverSupplierPickupsView;
import com.wm.brta.domain.Role;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.domain.UserAccount;
import com.wm.brta.dto.CheckUniqueUserDTO;

@Repository
public class UserDao {

	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	public String save(User transientInstance, Role role) {
		System.out.println("transientInstance = "+transientInstance);
		System.out.println("role is = "+role);
		
		String msg = "success";
		try{
		Session session = sessionFactory.getCurrentSession();
		long id = 0;
		Transaction tx = session.beginTransaction();
		try {
			id = (long) session.save(transientInstance);
			tx.commit();
		} catch (RuntimeException e) {
			msg = "fail";
			LOGGER.error("save in save of UserdAO"+e);
		}
		
		Query queryDriver = session
				.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
						+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description) from User usr where usr.userId =:userId");
		queryDriver.setParameter("userId", id);

		List<User> userlist = null;
		userlist = queryDriver.list();

		UserAccount useraccount = new UserAccount();

		for (User userac : userlist) {
			useraccount.setUser(userac);
			useraccount.setCreatedDate(userac.getCreateDate());
			useraccount.setUpdatedAt(userac.getUpdatedDate());
			useraccount.setUpdatedBy(userac.getUpdatedBy());
			useraccount.setEnabled(true);
			useraccount.setRole(role);
			try {

				long idx = (long) session.save(useraccount);
				tx.commit();
				msg = "success";
			} catch (RuntimeException ep) {
				msg = "success";
				LOGGER.error("Error in UserAccount "+ep);
			}
		}
		}catch(Exception e){
			LOGGER.error("Error in UserDAO: "+e);
			return null;
		}
		return msg;

	}

	public List<User> getAllUsers(User user) {
		Session session = sessionFactory.getCurrentSession();
	
		List<User> users = new ArrayList<User>();

		try {
			Query query = null;

			String stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
					+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
					+ " where userA.user.userId = usr.userId "
					+ " order by usr.createDate desc";

			if ((user.getRoleId() != null) && (user.getRoleId() > 0)) {

				if (user.getStatusFilter() != null) {

					stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
							+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
							+ " where userA.user.userId = usr.userId and userA.role.roleId =:roleId and usr.supplier.supplierId =:supplierId and  "
							+ " usr.enabled =:enabled "
							+ " order by usr.createDate desc";

					query = session.createQuery(stringQuery);
					query.setParameter("enabled", user.getStatusFilter()
							.equals("Active") ? true : false);
					query.setParameter("roleId", user.getRoleId());
					query.setParameter("supplierId", user.getSupplierFilter());

				} else {
					stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
							+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
							+ " where userA.user.userId = usr.userId and userA.role.roleId =:roleId and usr.supplier.supplierId =:supplierId  "
							+ " order by usr.createDate desc";
					query = session.createQuery(stringQuery);
					query.setParameter("roleId", user.getRoleId());
					query.setParameter("supplierId", user.getSupplierFilter());
				}

			} else {

				if ((user.getSupplierFilter() != null)
						&& (user.getSupplierFilter() > 0)) {

					if ((user.getStatusFilter() != null)
							&& (user.getStatusFilter() != "")) {
						stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
								+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA"
								+ " where userA.user.userId = usr.userId and usr.supplier.supplierId =:supplierId"
								+ " and usr.enabled =:enabled  order by usr.createDate desc";
						query = session.createQuery(stringQuery);
						query.setParameter("enabled", user.getStatusFilter()
								.equals("Active") ? true : false);
						query.setParameter("supplierId",
								user.getSupplierFilter());

					} else {
						stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled, "
								+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
								+ " where userA.user.userId = usr.userId and usr.supplier.supplierId =:supplierId "
								+ " order by usr.createDate desc";
						query = session.createQuery(stringQuery);
						query.setParameter("supplierId",
								user.getSupplierFilter());
					}
				} else {
					if ((user.getStatusFilter() != null)
							&& (user.getStatusFilter() != "")) {
						stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
								+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA"
								+ " where userA.user.userId = usr.userId "
								+ " and usr.enabled =:enabled  order by usr.createDate desc";
						query = session.createQuery(stringQuery);
						query.setParameter("enabled", user.getStatusFilter()
								.equals("Active") ? true : false);

					} else {

						stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
								+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA"
								+ " where userA.user.userId = usr.userId "
								+ "  order by usr.createDate desc";
						query = session.createQuery(stringQuery);
					}
				}
			}

			users = query.list();
			

		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getAllUsers=====>" + e);
		}
		return users;
	}

	public Boolean merge(User user) {

		Session session = sessionFactory.getCurrentSession();
		long id = 0;
		boolean status=false;
		try {
			String stringQuery = "update User set firstName =:firstName , lastName =:lastName ,"
					+ "updatedBy =:updatedBy, emailId =:emailId, mobilePhone =:mobilePhone, enabled =:isEnabled,"
					+ " supplier.supplierId =:supplierId  where userId =:userId";

			Query query = session.createQuery(stringQuery);
			query.setParameter("firstName", user.getFirstName());
			query.setParameter("lastName", user.getLastName());
			query.setParameter("updatedBy", user.getUpdatedBy());
			query.setParameter("emailId", user.getEmailId());
			query.setParameter("mobilePhone", user.getMobilePhone());
			query.setParameter("isEnabled", user.isEnabled());
			query.setParameter("supplierId", user.getSupplier().getSupplierId());		
			query.setParameter("userId", user.getUserId());

			Integer updateSuccessful = query.executeUpdate();

			if (updateSuccessful > 0) {
				status = true;
			}
			
		} catch (RuntimeException e) {

			LOGGER.error("Error:UserDao:merge=====>" + e);
		} finally {

		}

		return status;
	}

	public Boolean updateUserRole(Long userId, Integer roleId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;

		Boolean status = false;
		String stringQuery = "update UserAccount set role.roleId =:roleId where user.userId =:userId";
		try {
		Query query = session.createQuery(stringQuery);
		
			query.setParameter("roleId", roleId);
			query.setParameter("userId", userId);
			Integer updateSuccessful = query.executeUpdate();
			if (updateSuccessful > 0) {
				status = true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getUserByEmailAndPhone=====>" + e);
		} finally {

		}

		return true;

	}

	public List<User> getUserByEmailAndPhone(User user) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;

		String stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description, userA.role.roleId) from User usr, UserAccount userA "
				+ " where userA.user.userId = usr.userId and usr.emailId=:email and usr.mobilePhone=:mobile and usr.enabled=:status "
				+ " order by usr.createDate desc";

		Query query = session.createQuery(stringQuery);
		try {
			query.setParameter("email", user.getEmailId().trim());
			query.setParameter("mobile", user.getMobilePhone());
			query.setParameter("status", true);
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getUserByEmailAndPhone=====>" + e);
		} finally {

		}
		return users;
	}

	public List<Role> getAllRoleFromUser(User user, String roleDescription) {
		Session session = sessionFactory.getCurrentSession();

		List<Role> roles = new ArrayList<Role>();

		try {

			Query query = session
					.createQuery("select userAccount.role from UserAccount userAccount "
							+ " where userAccount.user.userId =:userId and userAccount.role.roleDescription=:roleDescription "
							+ " and userAccount.isEnabled=:status");
			query.setParameter("userId", user.getUserId());
			query.setParameter("roleDescription", roleDescription);
			query.setParameter("status", true);

			roles = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao: getAllRoleFromUser=====>" + e);
		} finally {

		}

		return roles;
	}

	public List<User> getAllUsers(Supplier supplier) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		try {
		String stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
				+ " where userA.user.userId = usr.userId and usr.supplier.supplierId=:supplierId "
				+ " and usr.enabled=true order by usr.createDate desc";
		Query query = session.createQuery(stringQuery);

		
			query.setParameter("supplierId", supplier.getSupplierId());
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getAllUsers=====>" + e);
		} finally {

		}
		return users;
	}

	public List<User> getOnlyDrivers(Integer supplierId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;

		String stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
				+ " where userA.user.userId = usr.userId and usr.supplier.supplierId=:supplierId "
				+ " and usr.enabled=true and userA.role.roleId=1 order by usr.createDate desc";
		Query query = session.createQuery(stringQuery);

		try {
			query.setParameter("supplierId", supplierId);
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getAllUsers=====>" + e);
		} finally {

		}
		return users;
	}

	// by deepak
	public List<User> getAllUsersFromSupplierId(Integer supplierIdList) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		try {
		String stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
				+ " where userA.user.userId = usr.userId and usr.supplier.supplierId=:supplierIdList "
				+ " and usr.enabled=true order by usr.createDate desc";

		Query query = session.createQuery(stringQuery);
		
			query.setParameter("supplierIdList", supplierIdList);
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao: getAllUsersFromSupplierId=====>" + e);
		} finally {

		}
		return users;
	}

	public List<User> getAllActiveUsers(Supplier supplier) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		try {
		String stringQuery = "select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description,userA.role.roleId) from User usr, UserAccount userA "
				+ " where userA.user.userId = usr.userId and usr.supplier.supplierId=:supplierId and usr.enabled=true "
				+ " order by usr.createDate desc";
		Query query = session.createQuery(stringQuery);
		
			query.setParameter("supplierId", supplier.getSupplierId());
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getAllActiveUsers=====>" + e);
		} finally {

		}
		return users;
	}

	public User getUserById(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		try {
		Query query = session
				.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, "
						+ " usr.emailId, usr.mobilePhone,usr.enabled,"
						+ " usr.supplier.supplierId, usr.supplier.description) "
						+ "from User usr" + " where usr.userId =:userId");
		

			query.setParameter("userId", userId);
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getUserById=====>" + e);
		} finally {

		}
		return users.get(0);

	}

	public Boolean checkMobileNoUnique(String mobilePhone) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		Boolean status = false;
		try {
		Query query = session
				.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
						+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description) from User usr where usr.mobilePhone =:mobilePhone");
		

			query.setParameter("mobilePhone", mobilePhone);
			users = query.list();
			if (users.size() > 0) {
				status = true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:checkMobileNoUnique=====>" + e);
		} finally {

		}
		return status;

	}

	public Boolean checkEmailIdUnique(String emailId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		Boolean status = false;
		try {
		Query query = session
				.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
						+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description) from User usr where usr.emailId =:emailId");
		

			query.setParameter("emailId", emailId);
			users = query.list();
			if (users.size() > 0) {
				status = true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:checkEmailIdUnique=====>" + e);
		} finally {

		}
		return status;

	}

	public Integer getSupplierIdFromUser(Long userId) {
		Session session = sessionFactory.getCurrentSession();

		List<Supplier> suppliers = new ArrayList<Supplier>();
		Integer supplierId = null;
		try {
			Query query = session
					.createQuery("select new Supplier(supplier.supplierId, supplier.description) from User where userId=:userId and enabled = true");
			query.setParameter("userId", userId);

			suppliers = query.list();

			if (suppliers != null && suppliers.size() > 0) {
				supplierId = suppliers.get(0).getSupplierId();
			}
		} catch (Exception e) {
			LOGGER.error("======Exception : getSupplierIdFromUser " + e);
		}

		return supplierId;
	}

	public User getUserObjectFromUserId(Long userId) {
		Session session = sessionFactory.getCurrentSession();

		User user = null;
		try {
			Query query = session
					.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
							+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description) from User usr where usr.userId=:userId ");
			query.setParameter("userId", userId);

			List<User> userList = query.list();

			if (userList.size() > 0) {
				user = userList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("======Exception : getSupplierIdFromUser " + e);
		}

		return user;
	}

	public User checkUniqueUser(CheckUniqueUserDTO checkUniqueUser) {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();

		User user = null;
		try {
		Query query = session
				.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
						+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description, userA.role.roleId) from User usr, UserAccount userA "
						+ " where usr.emailId =:emailId and usr.mobilePhone =:mobilePhone and usr.supplier.supplierId =:supplierId and userA.user.userId = usr.userId");
		

			query.setParameter("emailId", checkUniqueUser.getEmailId());
			query.setParameter("mobilePhone", checkUniqueUser.getMobileNumber());
			query.setParameter("supplierId", checkUniqueUser.getSupplierId());
			users = query.list();

			if (users.size() > 0) {
				user = users.get(0);
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:checkEmailIdUnique=====>" + e);
		} finally {

		}

		return user;

	}
	
	public List<DriverSupplierPickupsView> getUserFromStoreId(Integer customerSiteId, Integer supplierId ) {
		
		List<DriverSupplierPickupsView> userIds=null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("select new DriverSupplierPickupsView(dspv.driver1Id , dspv.driver2Id) from DriverSupplierPickupsView dspv "
						+ "where customerSiteId =:customerSiteId  and supplierId =:supplierId");
		try {

			query.setParameter("customerSiteId", customerSiteId);
			query.setParameter("supplierId", supplierId);
			userIds = query.list();
			
		} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:checkMobileNoUnique=====>" + e);
		} finally {

		}
		
		return userIds;
	}

}
