package ru.itmo.lab4.repositories;

import ru.itmo.lab4.models.UserEntity;
import ru.itmo.lab4.payload.AuthRequest;
import ru.itmo.lab4.util.PasswordUtils;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserRepository extends GenericRepositoryJPA<UserEntity> {

    public UserEntity findByUserAndPass(AuthRequest userRequested) {
        try {
            TypedQuery<UserEntity> query = em.createNamedQuery(UserEntity.QUERY_VALIDATE_LOGIN, UserEntity.class);
            query.setParameter("username", userRequested.getUsername());
            query.setParameter("password", PasswordUtils.digestPassword(userRequested.getPassword()));
            UserEntity user = query.getSingleResult();
            if (user == null) throw new SecurityException("Invalid user/password");
            return user;
        } catch (NoResultException e) {
            throw new SecurityException("Invalid user/password");
        }
    }

    public UserEntity findByUser(String username) {
        try {
            TypedQuery<UserEntity> query = em.createNamedQuery(UserEntity.QUERY_FIND_BY_USERNAME, UserEntity.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
