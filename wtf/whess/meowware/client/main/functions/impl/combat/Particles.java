package wtf.whess.meowware.client.main.functions.impl.combat;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.AttackEntityEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class Particles extends Function {
    private final NumberSetting multiplier = new NumberSetting("Multiplier", 1, 1, 15);

    public Particles() {
        super("Particles", Category.combat);
        addSetting(multiplier);
    }

    @EventTarget
    public void onAttackEntity(AttackEntityEvent event) {
        for (int i = 0; i <= multiplier.getValue(); ++i) {
            mc.player.onEnchantmentCritical(event.getEntity());
        }
    }
}
