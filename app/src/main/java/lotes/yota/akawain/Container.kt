package lotes.yota.akawain

public class Container() {
	private var con = hashMapOf("test" to floatArrayOf(-0.5f, -0.5f, -0.5f,
			                 0.5f, -0.5f, -0.5f,
			                 0.5f, 0.5f, -0.5f,

			                 0.5f, 0.5f, -0.5f,
			                -0.5f, 0.5f, -0.5f,
			                -0.5f, -0.5f, -0.5f))

	public fun getVXS(key: String): FloatArray? {
		return con.get(key.concat("vxs"))
	}

	public fun getNXS(key: String): FloatArray? {
		return con.get(key.concat("nxs"))
	}

	public fun save(key: String, obj: ObjectData) {
		con.put(key.concat("vxs"), obj.vxs)
		con.put(key.concat("nxs"), obj.nxs)
	}
}