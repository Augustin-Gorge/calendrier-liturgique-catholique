package fr.plaisance.calit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class SaintsDuJour {

	@SuppressWarnings("unchecked")
	private final Map<String, Object> saints;

	private static final SaintsDuJour instance = new SaintsDuJour();

	private SaintsDuJour() {
		this.saints = chargerSaints();
	}

	public static SaintsDuJour getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> chargerSaints() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			java.net.URL resource = classLoader.getResource("saints.json");
			if (resource == null) {
				System.err.println("saints.json not found in classpath");
				return Map.of();
			}
			String saintsPath = new java.io.File(resource.toURI()).getAbsolutePath();
			String json = new String(Files.readAllBytes(Paths.get(saintsPath)), StandardCharsets.UTF_8);
			return new Gson().fromJson(json, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return Map.of();
		}
	}

	public String getSaintPrincipalDuJour(MonthDay date) {
		try {
			Map<String, Object> mois = (Map<String, Object>) saints.get(String.valueOf(date.getMonthValue()));
			if (mois == null) return null;
			List<String> saintsOfDay = (List<String>) mois.get(String.valueOf(date.getDayOfMonth()));
			if (saintsOfDay == null || saintsOfDay.isEmpty()) return null;
			String saint = (String) saintsOfDay.get(0);
			// Nettoyer le nom du saint (supprimer les espaces en fin)
			return saint != null ? saint.trim() : null;
		} catch (Exception e) {
			return null;
		}
	}
}
