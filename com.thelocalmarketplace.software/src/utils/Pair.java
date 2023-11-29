// Aleksandr Sokolov (30191754)
// Azariah Francisco (30085863)
// Brandon Smith (30141515)
// Carlos Serrouya (30192761)
// Diego de Jaraiz (30176017)
// Emily Willams (30122865)
// Evan Ficzere (30192404)
// Jaden Taylor (30113034)
// Joshua Bourchier (30194364)
// Justine Mangaliman (30164741)
// Kaelin Good (30092239)
// Laura Yang（30156356)
// Myra Latif (30171760)
// Noelle Thundathil (30115430)
// Raj Rawat (30173990)
// Roshan Patel (30184010)
// Sam Fasakin (30161903)
// Simon Bondad (30163401)
// Simon Oseen (30144175)
// Sohaib Zia (30160114)
// Sunny Hoang (30170708)
// Yasemin Khanmoradi (30066537)

package utils;

/**
 * This is just a container that binds two values together. Basically a C-style
 * struct.
 * 
 * This class doesn't care whether the object is null or not.
 * 
 * @param <K> a class
 * @param <V> a class
 */

public class Pair<K, V> {

	K key;
	V val;

	public Pair(K k, V v) {
		key = k;
		val = v;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return val;
	}

}
