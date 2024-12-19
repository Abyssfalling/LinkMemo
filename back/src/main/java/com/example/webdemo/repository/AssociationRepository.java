package com.example.webdemo.repository;

import com.example.webdemo.model.AssociationInfo;
import com.example.webdemo.model.UserCustomerAssociationInfo;

import java.util.List;

/**
 * 作为工厂类，提供依赖注入的接口
 */
public interface AssociationRepository {

    void insertAssociation (int uid,  int lid, int type);

    void insertCustomer(int uid, int mWid, int sWid, int type);

    void delete(int aid);

    void deleteCustomer(int aid);

    List<AssociationInfo> getDeleteList(int uid);

    List<UserCustomerAssociationInfo> getCustomerList(int uid, int mWid);

}
