package lotes.yota.akawain

import java.util.*	
public class Parser() {
	var src = """# Blender v2.75 (sub 0) OBJ File: ''
# Blender v2.75 (sub 0) OBJ File: ''
# www.blender.org
v -0.595627 -0.936978 0.501153
v -0.756396 1.316220 -0.035826
v -0.890639 -0.486633 -0.726759
v -1.051408 1.766566 -1.263738
v 0.702346 -0.681437 0.283031
v 0.541577 1.571762 -0.253949
v 0.407334 -0.231091 -0.944882
v 0.246565 2.022107 -1.481861
vn -0.973500 -0.015000 0.228400
vn -0.115200 -0.238100 -0.964400
vn 0.973500 0.015000 -0.228400
vn 0.115200 0.238100 0.964400
vn 0.119900 -0.922400 -0.367100
vn -0.119900 0.922400 0.367100
s off
f 2//1 4//1 3//1
f 4//2 8//2 7//2
f 6//3 5//3 7//3
f 2//4 1//4 5//4
f 1//5 3//5 7//5
f 8//6 4//6 2//6
f 1//1 2//1 3//1
f 3//2 4//2 7//2
f 8//3 6//3 7//3
f 6//4 2//4 5//4
f 5//5 1//5 7//5
f 6//6 8//6 2//6


"""
	private var vxs = ArrayList<Float>()
	private var nxs = ArrayList<Float>()
	private var fxs = ArrayList<Int>()

	private var finVxs = ArrayList<Float>()
	public fun Parse() {
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

	public fun getVertices(): FloatArray {
		var xs = FloatArray(finVxs.size())

		for (i in finVxs.indices) {
			xs[i] = finVxs[i]
		}

		return xs
	}
}
