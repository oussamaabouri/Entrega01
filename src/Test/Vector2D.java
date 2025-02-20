package Test;

public record Vector2D(Double x, Double y) {
	
	public Double modulo() {
		return Math.sqrt(this.x()*this.x() + this.y()*this.y());
	}
	
	public Double productoEscalar(Vector2D other) {
		return this.x()*other.x() + this.y()*other.y();
	}

	
	public Double anguloRadianes() {
		return Math.acos(this.x()/this.modulo());
	}
	
	public Double anguloGrados() {
		return Math.toDegrees(Math.acos(this.x()/this.modulo()));
	}
	
	public Double angulo(Vector2D other) {
		return Math.acos(this.productoEscalar(other)/(this.modulo()*other.modulo()));
	}
	
	
	public static Vector2D of(Double x, Double y) {
		return new Vector2D(x, y);
	}
	public Double area(Vector2D other) {
		return (this.modulo()*other.modulo()*Math.sin(this.angulo(other)))/2;
	}
	
	@Override
	public String toString() {
		return String.format("(%.1f, %.1f)", this.x(), this.y());
	}
	
	
	
	public static void main(String[] args) {
		Vector2D v = new Vector2D(1., 0.);
		Vector2D w = new Vector2D(0., 1.);
		System.out.println(v.modulo());
		System.out.println(v.productoEscalar(w));
		System.out.println(v.toString());
		System.out.println(v.anguloRadianes());
		System.out.println(v.angulo(w));
		System.out.println(v.area(w));
	}

}
