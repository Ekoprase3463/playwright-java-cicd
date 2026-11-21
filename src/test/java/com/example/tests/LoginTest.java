package com.example.tests;

import com.example.base.BaseTest;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Login Berhasil dengan Kredensial Valid")
    void testValidLogin() {
        page.navigate("https://the-internet.herokuapp.com/login");

        // Isi form
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        page.locator("button[type='submit']").click();

        // Verifikasi alert pesan sukses
        Locator flashMessage = page.locator("#flash");
        assertThat(flashMessage).isVisible();
        assertThat(flashMessage).containsText("You logged into a secure area!");

        // Verifikasi tombol logout ada
        assertThat(page.locator("a[href='/logout']")).isVisible();
    }

    @Test
    @DisplayName("Login Gagal jika Password Salah")
    void testInvalidPassword() {
        page.navigate("https://the-internet.herokuapp.com/login");

        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("PasswordSalah123");
        page.locator("button[type='submit']").click();

        // Verifikasi pesan error
        Locator flashMessage = page.locator("#flash");
        assertThat(flashMessage).isVisible();
        assertThat(flashMessage).containsText("Your password is invalid!");
    }
}
