package com.example.demo.mock;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.post.controller.PostController;
import com.example.demo.post.controller.PostCreateController;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.service.PostServiceImpl;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.controller.UserController;
import com.example.demo.user.controller.UserCreateController;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.port.MailSender;
import com.example.demo.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final UserService userService;
    public final PostService postService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userServiceImpl = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .certificationService(this.certificationService)
                .userRepository(this.userRepository)
                .build();
        this.userService = UserServiceImpl.builder()
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .clockHolder(clockHolder)
                .uuidHolder(uuidHolder)
                .build();
        this.userController = UserController.builder()
                .userService(this.userService)
                .build();
        this.userCreateController = UserCreateController.builder()
                .userService(this.userService)
                .build();
        this.postController = PostController.builder()
                .postService(postService)
                .userController(userController)
                .build();
        this.postCreateController = PostCreateController.builder()
                .postService(postService)
                .build();
    }
}
