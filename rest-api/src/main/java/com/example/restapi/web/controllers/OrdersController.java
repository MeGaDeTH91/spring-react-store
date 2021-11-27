package com.example.restapi.web.controllers;

import com.example.restapi.messages.ProductMessages;
import com.example.restapi.messages.UserMessages;
import com.example.restapi.model.service.OrderServiceModel;
import com.example.restapi.model.service.UserServiceModel;
import com.example.restapi.model.view.OrderViewModel;
import com.example.restapi.model.view.OrdersCollectionModel;
import com.example.restapi.service.OrderService;
import com.example.restapi.service.ShoppingCartService;
import com.example.restapi.service.UserService;
import com.example.restapi.util.JSONResponse;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OrdersController(OrderService orderService, ShoppingCartService shoppingCartService, UserService userService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getUserOrders(@PathVariable Long id) {
        List<OrderServiceModel> orders = orderService.getUserOrders(id);
        if (orders == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString("Problem occurred while getting user orders."));
        }

        OrdersCollectionModel resultOrders = new OrdersCollectionModel();
        resultOrders.setOrders(orders
                .stream()
                .map(order -> modelMapper.map(order, OrderViewModel.class))
                .collect(Collectors.toSet()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultOrders);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> create(HttpServletRequest request) throws IOException {
        Long userId = getUserId(request);
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        }

        UserServiceModel user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_DOES_NOT_EXIST));
        }
        if (user.getCart() == null || user.getCart().getProducts().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_CART_IS_EMPTY));
        }

        OrderServiceModel order = new OrderServiceModel();
        order.setCustomer(user);
        order.setProducts(user.getCart().getProducts());

        OrderServiceModel resultOrder = orderService.create(order);
        if (resultOrder == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        }

        shoppingCartService.emptyCart(user.getCart().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
    }

    @PostMapping(value = "/add-to-cart/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> addToCart(@PathVariable Long id, HttpServletRequest request) throws IOException {
        Long userId = getUserId(request);
        if (id == null || userId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        }

        UserServiceModel user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_DOES_NOT_EXIST));
        }

        if (shoppingCartService.addProduct(user.getCart().getId(), id)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        }
    }

    @DeleteMapping(value = "/remove-from-cart/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> removeFromCart(@PathVariable Long id, HttpServletRequest request) throws IOException {
        Long userId = getUserId(request);
        if (id == null || userId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        }

        UserServiceModel user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_DOES_NOT_EXIST));
        }

        if (shoppingCartService.removeProduct(user.getCart().getId(), id)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(ProductMessages.UPDATE_NOT_SUCCESSFUL));
        }
    }

    private Long getUserId(HttpServletRequest request) throws IOException {
        JSONObject requestBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        String userId = requestBody.get("userId").toString();
        if (userId == null) {
            return null;
        }

        return Long.parseLong(userId);
    }
}
