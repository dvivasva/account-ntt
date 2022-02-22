package com.dvivasva.account.utils;


import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import org.springframework.beans.BeanUtils;

public class AccountUtil {

    public static AccountDto entityToDto(Account account){
        var AccountDto=new AccountDto();
        BeanUtils.copyProperties(account,AccountDto);
        return AccountDto;
    }
    public static Account dtoToEntity(AccountDto accountDto){
        var entity=new Account();
        BeanUtils.copyProperties(accountDto,entity);
        return entity;
    }

}
