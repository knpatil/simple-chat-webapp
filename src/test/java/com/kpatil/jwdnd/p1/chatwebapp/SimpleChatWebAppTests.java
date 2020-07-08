package com.kpatil.jwdnd.p1.chatwebapp;

import com.kpatil.jwdnd.p1.chatwebapp.model.ChatMessage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleChatWebAppTests {

    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    void setUp() {
        baseURL = "http://localhost:" + port;
    }


    @Test
    void testUserSignupLoginAndSubmitMessage() throws InterruptedException {
        String username = "kpatil";
        String password = "thatsmypassword";
        String messageText = "Hello There!";

        driver.get(baseURL + "/signup");

        // signup
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Kamlakar", "Patil", username, password);

        Thread.sleep(3000);

        // login
        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Thread.sleep(3000);

        ChatPage chatPage = new ChatPage(driver);
        chatPage.sendChatMessage(messageText);

        ChatMessage sentMessage = chatPage.getFirstMessage();

        assertEquals(username, sentMessage.getUsername());
        assertEquals(messageText, sentMessage.getMessageText());

        Thread.sleep(3000);

    }

}
