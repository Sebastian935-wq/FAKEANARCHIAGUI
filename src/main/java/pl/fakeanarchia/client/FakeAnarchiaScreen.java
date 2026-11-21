package pl.fakeanarchia.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class FakeAnarchiaScreen extends Screen {
    private enum Tab { KLUCZE, CZEKI, STATYSTYKI, EVENTOWKI }

    private record FakeEntry(String name, Item icon, int color, String[] lore) {}

    private final List<FakeEntry> keys = List.of(
            new FakeEntry("Klucz do epickiej skrzynki", Items.TRIPWIRE_HOOK, 0xFFD12CCB,
                    new String[]{"", "» Klucz ten służy do otworzenia skrzynki,", "  która znajduje się na /spawn", "", "» FAKE PRZEDMIOT — tylko GUI"})
    );
    private final List<FakeEntry> cheques = List.of(
            new FakeEntry("Czek pieniężny - $1000", Items.PAPER, 0xFF00D52A,
                    new String[]{"» Ten papier wartościowy został utworzony", "» Na kwotę początkową +1000$!", "", "» UWAGA! Nowe czeki tracą swoją wartość", "  o 2% ich startowej wartości z każdą godziną.", "", "» Kliknij FAJNYM, aby wykorzystać.", "» To lokalna atrapa — nic nie wysyła na serwer."}),
            new FakeEntry("Czek pieniężny - $10000", Items.PAPER, 0xFF00D52A,
                    new String[]{"» Wartość czeku: $10000", "» Kwota wyświetlana pełną liczbą.", "» Lokalna atrapa."}),
            new FakeEntry("Czek pieniężny - $100000", Items.PAPER, 0xFF00D52A,
                    new String[]{"» Wartość czeku: $100000", "» Kwota wyświetlana pełną liczbą.", "» Lokalna atrapa."})
    );
    private final List<FakeEntry> eventItems = List.of(
            new FakeEntry("Totem ułaskawienia", Items.TOTEM_OF_UNDYING, 0xFFE02ACB,
                    new String[]{"» Jest to przedmiot zdobyty podczas", "  Wydarzenia Halloweenowego 2026!", "", "» Trzymając go po śmierci nie", "  stracisz swoich przedmiotów!"}),
            new FakeEntry("Snoczy miecz", Items.NETHERITE_SWORD, 0xFFE02ACB,
                    new String[]{"Ostrość VI", "Zaklęty ogień II", "Dodatkowe obrażenia 23%", "", "» Posiada on możliwość strzelania", "  perłami kresu"}),
            new FakeEntry("Kosa", Items.IRON_HOE, 0xFFE02ACB,
                    new String[]{"» Jest to przedmiot zdobyty podczas", "  Wydarzenia Halloweenowego 2026!", "", "» Uderz gracza, a oboje traficie", "  do innego wymiaru na 12 sekund.", "» Inni gracze znikają, a ty dostajesz", "  szybkość, pośpiech i regenerację!"}),
            new FakeEntry("Wyrzutnia Hydro Klatki", Items.PRISMARINE_SHARD, 0xFF00D8D8,
                    new String[]{"» Jest to przedmiot zdobyty podczas", "  wydarzenia wakacyjnego 2026!", "", "» Po użyciu wystrzeliwuje pocisk, który", "  tworzy wodną klatkę więżącą gracza", "  przeciwników w miejscu!"})
    );

    private Tab selectedTab = Tab.KLUCZE;
    private int selectedIndex = 0;
    private int panelX, panelY, panelW, panelH;

    public FakeAnarchiaScreen() {
        super(Text.literal("ANARCHIA.GG — Fake GUI"));
    }

    @Override
    protected void init() {
        panelW = Math.min(430, this.width - 24);
        panelH = Math.min(250, this.height - 24);
        panelX = (this.width - panelW) / 2;
        panelY = (this.height - panelH) / 2;
        selectedIndex = 0;
    }

    private List<FakeEntry> currentEntries() {
        return switch (selectedTab) {
            case KLUCZE -> keys;
            case CZEKI -> cheques;
            case EVENTOWKI -> eventItems;
            case STATYSTYKI -> List.of();
        };
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        // Minecraft-like dark menu frame
        ctx.fill(panelX - 3, panelY - 3, panelX + panelW + 3, panelY + panelH + 3, 0xFF080808);
        ctx.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0xFF303030);
        ctx.fill(panelX + 2, panelY + 2, panelX + panelW - 2, panelY + 25, 0xFF202020);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("ANARCHIA.GG" ).formatted(Formatting.BOLD), width / 2, panelY + 8, 0xFFFFFF);

        int tabY = panelY + 34;
        String[] labels = {"Klucze", "Czeki", "Statystyki", "Eventówki"};
        Tab[] tabs = Tab.values();
        int tabW = (panelW - 20) / 4;
        for (int i = 0; i < tabs.length; i++) {
            int x = panelX + 10 + i * tabW;
            boolean active = selectedTab == tabs[i];
            drawButton(ctx, x, tabY, tabW - 3, 22, active);
            ctx.drawCenteredTextWithShadow(textRenderer, Text.literal(labels[i]), x + (tabW - 3) / 2, tabY + 7, active ? 0xFFFFFF : 0xC0C0C0);
        }

        int contentY = tabY + 32;
        int listX = panelX + 10;
        int listW = Math.max(120, panelW / 2 - 15);
        int detailX = listX + listW + 8;
        int detailW = panelX + panelW - 10 - detailX;
        ctx.fill(listX, contentY, listX + listW, panelY + panelH - 30, 0xFF242424);
        ctx.fill(detailX, contentY, detailX + detailW, panelY + panelH - 30, 0xFF242424);

        List<FakeEntry> entries = currentEntries();
        if (selectedTab == Tab.STATYSTYKI) {
            ctx.drawTextWithShadow(textRenderer, Text.literal("FAKE STATYSTYKI" ).formatted(Formatting.AQUA, Formatting.BOLD), listX + 8, contentY + 9, 0xFFFFFF);
            ctx.drawTextWithShadow(textRenderer, Text.literal("Kolejne dane dodamy ze screenów."), listX + 8, contentY + 27, 0xBBBBBB);
            ctx.drawTextWithShadow(textRenderer, Text.literal("To miejsce jest przygotowane."), listX + 8, contentY + 40, 0xBBBBBB);
            ctx.drawTextWithShadow(textRenderer, Text.literal("Nie wymyślam statystyk serwera."), detailX + 8, contentY + 10, 0xAAAAAA);
        } else if (entries.isEmpty()) {
            ctx.drawTextWithShadow(textRenderer, Text.literal("Brak przedmiotów"), listX + 8, contentY + 10, 0xFFFFFF);
        } else {
            for (int i = 0; i < entries.size(); i++) {
                int y = contentY + 5 + i * 27;
                boolean active = i == selectedIndex;
                if (active) ctx.fill(listX + 3, y, listX + listW - 3, y + 24, 0xFF454545);
                ctx.drawItem(new ItemStack(entries.get(i).icon()), listX + 7, y + 3);
                ctx.drawTextWithShadow(textRenderer, Text.literal(entries.get(i).name()), listX + 29, y + 8, entries.get(i).color());
            }
            FakeEntry entry = entries.get(Math.min(selectedIndex, entries.size() - 1));
            ctx.drawItem(new ItemStack(entry.icon()), detailX + 8, contentY + 8);
            ctx.drawTextWithShadow(textRenderer, Text.literal(entry.name()).formatted(Formatting.BOLD), detailX + 30, contentY + 13, entry.color());
            int loreY = contentY + 39;
            for (String line : entry.lore()) {
                ctx.drawTextWithShadow(textRenderer, Text.literal(line), detailX + 8, loreY, line.contains("UWAGA") ? 0xFFFF5555 : 0xFFBBBBBB);
                loreY += 12;
            }
        }

        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Lokalne atrapy • nie działają na serwerze • ESC: zamknij"), width / 2, panelY + panelH - 17, 0xFFAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawButton(DrawContext ctx, int x, int y, int w, int h, boolean active) {
        ctx.fill(x, y, x + w, y + h, active ? 0xFF777777 : 0xFF555555);
        ctx.fill(x + 1, y + 1, x + w - 1, y + h - 1, active ? 0xFF555555 : 0xFF3A3A3A);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        int tabY = panelY + 34;
        int tabW = (panelW - 20) / 4;
        if (mouseY >= tabY && mouseY <= tabY + 22) {
            int index = (int) ((mouseX - (panelX + 10)) / tabW);
            if (index >= 0 && index < 4) {
                selectedTab = Tab.values()[index];
                selectedIndex = 0;
                return true;
            }
        }
        int contentY = tabY + 32;
        int listX = panelX + 10;
        int listW = Math.max(120, panelW / 2 - 15);
        List<FakeEntry> entries = currentEntries();
        if (!entries.isEmpty() && mouseX >= listX && mouseX <= listX + listW && mouseY >= contentY) {
            int index = (int) ((mouseY - contentY - 5) / 27);
            if (index >= 0 && index < entries.size()) selectedIndex = index;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
