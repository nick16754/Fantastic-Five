import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class SaveExclusionStrategy implements ExclusionStrategy {


    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == Player.class && f.getName().equalsIgnoreCase("currentTile")) ||
                (f.getDeclaringClass() == Player.class && f.getName().equalsIgnoreCase("piece"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
