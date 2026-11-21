package com.example.tests;

import com.example.base.BaseTest;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TodoAppTest extends BaseTest {

    @Test
    @DisplayName("User dapat menambah todo items dan menandai selesai")
    void testAddAndCompleteTodo() {
        // 1. Kunjungi halaman TodoMVC
        page.navigate("https://demo.playwright.dev/todomvc");

        // 2. Buat locator input todo
        Locator newTodoInput = page.locator(".new-todo");

        // 3. Tambahkan 2 todo items
        newTodoInput.fill("Belajar CI/CD Automation");
        newTodoInput.press("Enter");

        newTodoInput.fill("Implementasi Playwright Java");
        newTodoInput.press("Enter");

        // 4. Verifikasi jumlah todo list
        Locator todoItems = page.locator(".todo-list li");
        assertThat(todoItems).hasCount(2);

        // 5. Ceklis item pertama sebagai 'completed'
        todoItems.first().locator(".toggle").check();

        // 6. Verifikasi item pertama memiliki class 'completed'
        assertThat(todoItems.first()).hasClass("completed");

        // 7. Verifikasi sisa item aktif adalah 1
        Locator todoCount = page.locator(".todo-count");
        assertThat(todoCount).containsText("1 item left");
    }

    @Test
    @DisplayName("User dapat memfilter todo berdasarkan status")
    void testFilterTodos() {
        page.navigate("https://demo.playwright.dev/todomvc");

        Locator newTodo = page.locator(".new-todo");
        newTodo.fill("Item 1");
        newTodo.press("Enter");
        newTodo.fill("Item 2");
        newTodo.press("Enter");

        // Centang salah satu
        page.locator(".todo-list li").first().locator(".toggle").check();

        // Klik filter 'Active'
        page.locator("a[href='#/active']").click();
        assertThat(page.locator(".todo-list li")).hasCount(1);
        assertThat(page.locator(".todo-list li label")).containsText("Item 2");

        // Klik filter 'Completed'
        page.locator("a[href='#/completed']").click();
        assertThat(page.locator(".todo-list li")).hasCount(1);
        assertThat(page.locator(".todo-list li label")).containsText("Item 1");
    }
}
