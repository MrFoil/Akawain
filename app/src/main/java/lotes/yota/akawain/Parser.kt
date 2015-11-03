package lotes.yota.akawain

import java.util.*	

public class Parser() {
	private var vxs = ArrayList<Float>()
	private var nxs = ArrayList<Float>()
	private var fxs = ArrayList<Int>()

	private var finVxs = ArrayList<Float>()

	public fun Parse(src: String) {
		src.lines().forEach {
			when (it.take(2)) {
				// Vertex
				"v " -> it.drop(2).split(" ").forEach({ x -> vxs.add(x.toFloat())})
				// Normals
				"vn" -> it.drop(3).split(" ").forEach({ x -> nxs.add(x.toFloat())})
				// Correspondings between
				"f " -> it.drop(2).split(" ").forEach({ v -> fxs.add(v.substringBefore("//").toInt())})
			}
		}

		for (f in fxs) {
			finVxs.add(vxs[(f-1)*3])
			finVxs.add(vxs[(f-1)*3+1])
			finVxs.add(vxs[(f-1)*3+2])
		}
	}
	public fun test(): FloatArray {
		return floatArrayOf(-0.5f, -0.5f, -0.5f,
			                 0.5f, -0.5f, -0.5f,
			                 0.5f, 0.5f, -0.5f,

			                 0.5f, 0.5f, -0.5f,
			                -0.5f, 0.5f, -0.5f,
			                -0.5f, -0.5f, -0.5f)
	}

	// We have to use float[] instead of ArrayList
	private fun toFloatArray(xs: ArrayList<Float>): FloatArray {
		var outxs = FloatArray(xs.size())
		for (i in xs.indices) {
			outxs[i] = xs[i]
		}
		return outxs
	}

	public fun getCubeVxs(): FloatArray {
		return toFloatArray(finVxs)
	}
	
}
