package com.example.FashionStore.MVCControllers;

import com.example.FashionStore.Models.*;
import com.example.FashionStore.Repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class HomeController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final CartOrdersRepository cartOrdersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RateReviewRepository rateReviewRepository;

    public HomeController(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository, OrdersRepository ordersRepository, CartOrdersRepository cartOrdersRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RateReviewRepository rateReviewRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.ordersRepository = ordersRepository;
        this.cartOrdersRepository = cartOrdersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.rateReviewRepository = rateReviewRepository;
    }

    private void setAuthenticationStateInModel(Model model, String loggedUser) {
        if (loggedUser == null || loggedUser.trim().equals("")) {
            model.addAttribute("loggedUser", "null");
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("isAdministrator", false);
        } else {
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("isAuthenticated", true);
            User user = userRepository.findById(Integer.parseInt(loggedUser)).orElse(null);
            if (user != null && user.getRole().getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("isAdministrator", true);
            } else {
                model.addAttribute("isAdministrator", false);
            }
        }
    }

    @GetMapping("/")
    public String getHomePage(Model model, @CookieValue(name = "logged-user") String loggedUser) {
        setAuthenticationStateInModel(model, loggedUser);
        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);
        return "Home";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id, Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            setAuthenticationStateInModel(model, loggedUser);
            Product product = productRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new Exception("Product not found"));
            List<RateReview> reviews = rateReviewRepository.findByProductProductId(product.getProductId());
            model.addAttribute("product", product);
            model.addAttribute("reviews", reviews);
            return "Product";
        } catch (Exception ex) {
            model.addAttribute("error", "Error occurred. Please try again.");
            return "Error";
        }
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable String id, Model model, @RequestParam(value = "quantity", defaultValue = "0") int quantity, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            Product product = productRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new Exception("Product not found"));
            User user = userRepository.findById(Integer.valueOf(loggedUser)).orElseThrow(() -> new Exception("User not found"));
            Cart cart = new Cart(
                    user,
                    product,
                    quantity,
                    "XL",
                    product.getPrice() * quantity,
                    false
            );
            cartRepository.save(cart);
            return "redirect:/app/cart";
        } catch (Exception ex) {
            System.out.println(ex);
            return "Error";
        }
    }

    @GetMapping("/cart")
    public String getCart(Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            List<Cart> carts = cartRepository.findByUserUserIdAndIsPurchased(Integer.parseInt(loggedUser), false);
            model.addAttribute("carts", carts);
            return "Cart";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @PostMapping("/delete-from-cart/{id}")
    public String deleteFromCart(@PathVariable String id, Model model, @RequestParam(value = "location", defaultValue = "cart") String location) {
        try {
            cartRepository.deleteById(Integer.parseInt(id));
            String val = null;
            if (location.equals("cart")) {
                val = "redirect:/app/cart";
            } else {
                val = "redirect:/app/checkout";
            }
            return val;
        } catch (Exception ex) {
            return "Error";
        }
    }

    @GetMapping("/checkout")
    public String checkoutCart(Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            List<Cart> items = cartRepository.findByUserUserIdAndIsPurchased(1, false);
            if (items.size() < 1) {
                return "Error";
            }
            model.addAttribute("items", items);
            return "Checkout";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @PostMapping("/checkout")
    public String postCheckoutCart(
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("code") String code,
            @RequestParam("card") String card,
            @RequestParam("date") String date,
            @RequestParam("cvv") String cvv,
            @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            User user = userRepository.findById(Integer.parseInt(loggedUser)).orElseThrow(() -> new Exception("User not found."));
            List<Cart> items = cartRepository.findByUserUserIdAndIsPurchased(user.getUserId(), false);
            if (items.size() < 1) {
                throw new Exception("No items in the cart.");
            }
            double val = 0;
            for (Cart item : items) {
                val += item.getQuantity() * item.getProduct().getPrice();
            }
            Orders order = new Orders(
                    new Date().toString(),
                    "PENDING",
                    user,
                    val
            );
            Orders savedOrder = ordersRepository.save(order);
            List<CartOrders> cart = new ArrayList<>();
            for (Cart item : items) {
                item.setPurchased(true);
                CartOrders cartOrders = new CartOrders(
                        item,
                        savedOrder
                );
                cart.add(cartOrders);
            }
            cartRepository.saveAll(items);
            cartOrdersRepository.saveAll(cart);
            return "redirect:/app/orders";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @GetMapping("/orders")
    public String getOrders(Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            List<Orders> orders = ordersRepository.findByUserUserId(Integer.parseInt(loggedUser));
            model.addAttribute("items", orders);
            return "Orders";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable String id, Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            Orders orders = ordersRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new Exception("Order not found."));
            List<CartOrders> cartOrders = cartOrdersRepository.findByOrders(orders);

            List<Cart> carts = cartOrders.stream()
                    .map(CartOrders::getCart)
                    .collect(Collectors.toList());

            model.addAttribute("items", carts);
            model.addAttribute("item", orders);
            return "Order";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @GetMapping("/register")
    public String registerUser(RedirectAttributes rd, Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser != null && !loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            String error = (String) rd.getFlashAttributes().get("error");
            String success = (String) rd.getFlashAttributes().get("success");
            if (error != null) {
                model.addAttribute("error", error);
            }
            if (success != null) {
                model.addAttribute("success", success);
            }
            return "Register";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @GetMapping("/login")
    public String loginUser(RedirectAttributes rd, Model model, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser != null && !loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            String error = (String) rd.getFlashAttributes().get("error");
            if (error != null) {
                model.addAttribute("error", error);
            }
            return "Login";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @PostMapping("/login")
    public String postLoginUser(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes rd, HttpServletResponse response, @CookieValue(name = "logged-user") String loggedUser) {
        try {
            if (loggedUser != null && !loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            User emailUser = userRepository.findByEmail(email);
            if (emailUser == null) {
                throw new Exception("User not found.");
            }
            if (!passwordEncoder.matches(password, emailUser.getPassword())) {
                throw new Exception("Invalid password. Please try again.");
            }
            Cookie cookie = new Cookie("logged-user", emailUser.getUserId().toString());
            cookie.setSecure(true);
            response.addCookie(cookie);
            return "redirect:/app/";
        } catch (Exception ex) {
            rd.addFlashAttribute("error", ex.getLocalizedMessage());
            return "redirect:/app/login";
        }
    }

    @GetMapping("/logout")
    public String getLogoutUser(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("logged-user", null);
            cookie.setSecure(true);
            response.addCookie(cookie);
            return "redirect:/app/";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @PostMapping("/register")
    public String postRegisterUser(
            @RequestParam(value = "email") String email,
            @RequestParam("password") String password,
            @RequestParam("number") String number,
            @RequestParam("username") String username,
            @RequestParam("confirm") String confirm,
            RedirectAttributes rd,
            @CookieValue(name = "logged-user") String loggedUser
    ) {
        try {
            if (loggedUser != null && !loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            User emailUser = userRepository.findByEmail(email);
            if (emailUser != null) {
                throw new Exception("User with given mail exists.");
            }
            User usernameUser = userRepository.findByUsername(username).orElse(null);
            if (usernameUser != null) {
                throw new Exception("User with given username exists.");
            }
            if (!confirm.equals(password)) {
                throw new Exception("Please provide matching passwords.");
            }
            String hashedPassword = passwordEncoder.encode(password);
            Role role = roleRepository.findByRole("USER");
            if (role == null) {
                throw new Exception("User role not found.");
            }
            User user = new User(
                    username,
                    email,
                    number,
                    hashedPassword,
                    role
            );
            userRepository.save(user);
            rd.addFlashAttribute("success", "User registered successfully!");
            return "redirect:/app/register";
        } catch (Exception ex) {
            rd.addFlashAttribute("error", ex.getLocalizedMessage());
            return "redirect:/app/register";
        }
    }

    @PostMapping("/add-review/{id}")
    public String addReview(@PathVariable("id") String id, @RequestParam("feedback") String feedback, @RequestParam("rating") String rating, @CookieValue("logged-user") String loggedUser) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            User user = userRepository.findById(Integer.parseInt(loggedUser)).orElseThrow(() -> new Exception("User not found"));
            Product product = productRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new Exception("Product not found"));
            int ratingVal = Integer.parseInt(rating);
            if (ratingVal < 1 || ratingVal > 5) {
                throw new Exception("Rating cannot be below 1 or above 5.");
            }
            RateReview rateReview = new RateReview(
                    user,
                    product,
                    ratingVal,
                    feedback,
                    new Date().toString()
            );
            rateReviewRepository.save(rateReview);
            return "redirect:/app/product/" + id;
        } catch (Exception ex) {
            return "Error";
        }
    }

    @GetMapping("/add-product")
    public String addProduct(@CookieValue("logged-user") String loggedUser, Model model) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            setAuthenticationStateInModel(model, loggedUser);
            return "AddProduct";
        } catch (Exception ex) {
            return "Error";
        }
    }

    @PostMapping("/add-product")
    public String addProductPost(@CookieValue("logged-user") String loggedUser,
                                 @RequestParam("name") String name,
                                 @RequestParam("category") String category,
                                 @RequestParam("price") String price,
                                 @RequestParam("image") String image,
                                 @RequestParam("quantity") String quantity,
                                 @RequestParam("description") String description) {
        try {
            if (loggedUser == null || loggedUser.trim().equals("")) {
                return "redirect:/app/";
            }
            Product product = new Product(
                    name,
                    description,
                    category,
                    Double.parseDouble(price),
                    Integer.parseInt(quantity),
                    image
            );
            productRepository.save(product);
            return "redirect:/app/";
        } catch (Exception ex) {
            return "Error";
        }
    }

}
