package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.AddressService;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.utils.SecurityUtil;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/list")
    public String getAddressPage() {
        return "myPage/address/address";
    }

    @GetMapping("/add")
    public String getAddressAddPage(AddressForm addressForm) {
        return "myPage/address/addressForm";
    }

    @GetMapping("/read")
    public String getAddressEditPage(@RequestParam Long addId , Model model) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        AddressForm addressForm = addressService.editPage(userEmail, addId);
        model.addAttribute("addressForm", addressForm);
        return "myPage/address/addressEdit";
    }

    @GetMapping("/addressList")
    @ResponseBody
    public List<AddressForm> getAddressList() {
        String userEmail = SecurityUtil.getCurrentMemberId();
        return addressService.getUserAddressList(userEmail);
    }

    @PostMapping("/add")
    public String registerAdd(AddressForm addressForm) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        addressService.saveAddress(userEmail, addressForm);
        return "redirect:/address/list";
    }

    @PostMapping("/update")
    public String updateAdd(AddressForm addressForm) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        addressService.updateAddress(userEmail, addressForm);
        return "redirect:/address/list";
    }

    @DeleteMapping("/{index}")
    public String deleteAdd(@PathVariable Long index) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        addressService.deleteAddress(userEmail, index);
        return "myPage/address/address";
    }

    @GetMapping("/default")
    @ResponseBody
    public AddressForm getDefaultAddress() {
        String userEmail = SecurityUtil.getCurrentMemberId();
        return addressService.getDefault(userEmail);
    }

}
