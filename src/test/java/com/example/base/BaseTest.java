package com.example.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

/**
 * BaseTest dengan fitur Rekam Video dan Tracing.
 */
public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(isHeadless)
                .setSlowMo(100)); // Sedikit diperlambat agar jalannya terlihat jelas di video
    }

    @BeforeEach
    void createContextAndPage(TestInfo testInfo) {
        // Konfigurasi context dengan REKAM VIDEO otomatis
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)
                .setRecordVideoDir(Paths.get("target/playwright-videos/"))
                .setRecordVideoSize(1280, 720));

        // Aktifkan juga Playwright Tracing
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @AfterEach
    void closeContext(TestInfo testInfo) {
        // Simpan file trace
        String traceName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9.-]", "_") + "-trace.zip";
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/playwright-traces/" + traceName)));

        // Menutup context otomatis merender dan menyimpan file video .webm
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
