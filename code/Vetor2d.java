public class Vetor2d {
  public double x;
  public double y;

  public Vetor2d() {
  }

  public Vetor2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vetor2d get() {
    return new Vetor2d(x, y);
  }

  public Vetor2d set(double x, double y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vetor2d add(Vetor2d v) {
    x += v.x;
    y += v.y;
    return this;
  }

  public Vetor2d add(double x, double y) {
    this.x += x;
    this.y += y;
    return this;
  }

  public Vetor2d mult(double n) {
    this.x *= n;
    this.y *= n;
    return this;
  }

  static public double dist(Vetor2d v1, Vetor2d v2) {
    double dx = v1.x - v2.x;
    double dy = v1.y - v2.y;
    return (double) Math.sqrt(dx * dx + dy * dy);
  }

  static public double dist(double x1, double y1, double x2, double y2) {
    double dx = x1 - x2;
    double dy = y1 - y2;
    return (double) Math.sqrt(dx * dx + dy * dy);
  }

  static public final double map(double value,
                                 double start1, double stop1,
                                 double start2, double stop2) {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
  }
}
