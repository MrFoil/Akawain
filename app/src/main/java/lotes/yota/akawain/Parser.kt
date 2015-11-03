package lotes.yota.akawain

import java.util.*	

data class ObjectData(val vxs: FloatArray, val nxs: FloatArray)

public class Parser() {
	public fun Parse(src: String): ObjectData {
		var vxs = ArrayList<Float>()
		var nxs = ArrayList<Float>()
		
		var fv = ArrayList<Int>()
		var fn = ArrayList<Int>()

		src.lines().forEach {
			when (it.take(2)) {
				// Vertex
				"v " -> it.drop(2).split(" ").forEach({ x -> vxs.add(x.toFloat())})
				// Normals
				"vn" -> it.drop(3).split(" ").forEach({ x -> nxs.add(x.toFloat())})
				// Correspondings between
				"f " -> it.drop(2).split(" ").forEach { x -> fn.add(x.substringAfter("//").toInt());
			                                                 fv.add(x.substringBefore("//").toInt()) }
			}
		}

		return ObjectData(finArray(fv, vxs), finArray(fn, nxs))
	}

	private fun finArray(indices: ArrayList<Int>, xs: ArrayList<Float>): FloatArray {
		var fin = ArrayList<Float>()

		// Duplicating 
		for (index in indices) {
			fin.add(xs[(index-1) * 3])
			fin.add(xs[(index-1) * 3 + 1])
			fin.add(xs[(index-1) * 3 + 2])
		}

		return toFloatArray(fin)
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
}
