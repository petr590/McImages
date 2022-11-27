package mcimages.block;

import java.util.HashMap;
import java.util.Map;

import mcimages.Direction;
import mcimages.Context;
import mcimages.Versions;

/**
 * Описывает блок. На один id может
 * быть несколько блоков, т.к. блок один и тот же,
 * только по-разному повёрнут.
 */
public class Block {
	
	/** Айди блока, используется в игре */
	protected final String id;
	
	/** Версия, в которой был добавлен блок */
	protected final int version;

	/** Кэш состояний блока */
	private Map<Direction, String> states = new HashMap<>(6);
	
	public Block(String id) {
		this(id, Versions.NONE);
	}
	
	public Block(String id, int version) {
		this.id = id;
		this.version = version;
	}
	
	public final String getId() {
		return id;
	}
	
	/** Можем ли мы использовать блок в данном контексте
	 * (например, сыпучие блоки нельзя использовать в горизонтальном изображении) */
	public boolean canUse(Context context) {
		return context.getMcVersion() >= version;
	}
	
	public final String getPlaceState(Direction direction) {
		if(states.containsKey(direction))
			return states.get(direction);
		
		String state = createPlaceState(direction);
		states.put(direction, state);
		return state;
	}
	
	protected String createPlaceState(Direction direction) {
		return id;
	}
}