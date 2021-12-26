package com.chenchao.controller.admin;

import com.chenchao.model.Shop;
import com.chenchao.model.Users;
import com.chenchao.service.IShopService;
import com.chenchao.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/store")
//@RequiresPermissions("store-manage")
public class AdminStoreController {

    @Autowired
    private IShopService shopService;

    @Autowired
    private UserService userService;

    @Value("3")
    private String business;

    @RequestMapping("/list")
//    @RequiresPermissions("store-list")
    public String storeList(Model model){

        List<Shop> stores = shopService.findStores();

        model.addAttribute("stores", stores);

        return "admin/store/admin_list";
    }

    @GetMapping("/toAddition")
//    @RequiresPermissions("store-add")
    public String addStore(Model model){
        List<Users> users = userService.findBusinesses(Integer.parseInt(business));
        model.addAttribute("users", users);
        return "admin/store/admin_add";
    }


    @RequestMapping("/{storeId}")
//    @RequiresPermissions("store-edit")
    public String toEdit(@PathVariable("storeId") int storeId, Model model){

        Shop store = shopService.findById(storeId);

        model.addAttribute("store", store);

        return "admin/store/admin_edit";
    }

    @PostMapping("/edit")
//    @RequiresPermissions("store-edit")
    public String editStore(Shop store,Model model){

        shopService.updateStore(store);

        model.addAttribute("saveMsg", "保存成功");

        return "forward:"+store.getShopId();
    }

    @RequestMapping("/deletion/{storeId}")
//    @RequiresPermissions("store-delete")
    public String deleteStore(@PathVariable("storeId") int storeId){
        shopService.deleteStore(storeId);
        return "redirect:/admin/store/list";
    }

    @RequestMapping("/addition")
//    @RequiresPermissions("store-add")
    public String addStore(Shop store){
        shopService.addStore(store);
        return "redirect:/admin/store/list";
    }

}
