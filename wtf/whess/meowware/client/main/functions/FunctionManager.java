package wtf.whess.meowware.client.main.functions;

import wtf.whess.meowware.client.main.functions.impl.combat.*;
import wtf.whess.meowware.client.main.functions.impl.exploit.*;
import wtf.whess.meowware.client.main.functions.impl.misc.*;
import wtf.whess.meowware.client.main.functions.impl.move.*;
import wtf.whess.meowware.client.main.functions.impl.visual.*;

import java.util.ArrayList;
import java.util.Comparator;

public final class FunctionManager {

    private final ArrayList<Function> modules = new ArrayList<>();

    public FunctionManager() {
        modules.add(new ClickGUI());
        modules.add(new Plugins());
        modules.add(new HUD());
        modules.add(new CameraClip());
        modules.add(new FreeCam());
        modules.add(new InventoryMove());
        modules.add(new OffHandCrash());
        modules.add(new NoItems());
        modules.add(new MemoryFix());
        modules.add(new FunctionList());
        modules.add(new AutoLeave());
        modules.add(new ChinaHat());
        modules.add(new Trails());
        modules.add(new JumpCircles());
        modules.add(new SwingAnimation());
        modules.add(new ViewModel());
        modules.add(new Velocity());
        modules.add(new KeepSprint());
        modules.add(new NoPush());
        modules.add(new Particles());
        modules.add(new AirJump());
        modules.add(new DamageFly());
        modules.add(new Aura());
        modules.add(new Fly());
        modules.add(new Speed());
        modules.add(new Strafe());
        modules.add(new Glide());
        modules.add(new Spider());
        modules.add(new Jesus());
        modules.add(new AntiBot());
        modules.add(new NoServerRotate());
        modules.add(new Timer());
        modules.add(new NoDelay());
        modules.add(new MiddleClickPearl());
        modules.add(new DeathCoordinates());
        modules.add(new AutoRespawn());
        modules.add(new AutoTotem());
        modules.add(new HighJump());
        modules.add(new NoClip());
        modules.add(new NoSlowDown());
        modules.add(new ESP());
        modules.add(new NoRender());
        modules.add(new FogColor());
        modules.add(new TargetHUD());
        modules.add(new Sprint());

        modules.sort(Comparator.comparingInt(module -> (int) module.getName().charAt(0)));
    }

    public ArrayList<Function> getFunctions() {
        return modules;
    }

    public Function getFunctionByName(final String name) {
        for (Function module : modules) {
            if (name.equalsIgnoreCase(module.getName())) {
                return module;
            }
        } return null;
    }

    public Function getFunctionClass(final Class<?> module) {
        for (Function mod : getFunctions()) {
            if (mod.getClass().equals(module)) {
                return mod;
            }
        } return null;
    }

    public ArrayList<Function> getFunctionsFromCategory(Category category) {
        final ArrayList<Function> modsInCategory = new ArrayList<Function>();
        for (Function mod : getFunctions()) {
            if (mod.getCategory() == category) {
                modsInCategory.add(mod);
            }
        } return modsInCategory;
    }

}
