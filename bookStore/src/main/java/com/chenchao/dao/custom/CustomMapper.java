package com.chenchao.dao.custom;

import java.util.List;
import com.chenchao.model.Permissions;
import com.chenchao.model.CharacterAlter;
import com.chenchao.model.Users;
import com.chenchao.model.custom.OrderCustom;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomMapper {
    List<OrderCustom> findOrdersByUserId(int userId);

    List<OrderCustom> findOrdersByStoreId(int storeId);

    List<CharacterAlter> findCharacterByUserId(int userId);

    List<Permissions> findPermissionsByRoleId(int characterId);

    List<Users> findBusinesses(int roleId);

    List<CharacterAlter> findRolesByUserId(int userId);
}
