package com.team.angular.interactiondesignapi.transfertobjects.admin;

import com.team.angular.interactiondesignapi.models.Admin;

import java.util.List;
import java.util.stream.Collectors;

public class Admin2AdminOutTO {
    public static AdminOutTO apply(Admin in) {
        AdminOutTO out = new AdminOutTO();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setSurname(in.getSurname());
        out.setPassword(in.getPassword());
        out.setEmail(in.getEmail());

        return out;
    }

    public static List<AdminOutTO> apply(List<Admin> admins) {
        return admins.stream().map(u -> apply(u)).collect(Collectors.toList());
    }
}
