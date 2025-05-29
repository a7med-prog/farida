const canvas = document.getElementById("heartCanvas");
const ctx = canvas.getContext("2d");
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

const romanticColors = ["#ff69b4", "#ff1493", "#db7093", "#ffc0cb", "#ff6ec7"];
let particles = [];

function heartFunction(t) {
  const x = 16 * Math.pow(Math.sin(t), 3);
  const y =
    13 * Math.cos(t) -
    5 * Math.cos(2 * t) -
    2 * Math.cos(3 * t) -
    Math.cos(4 * t);
  return { x, y };
}

class Particle {
  constructor(x, y, color) {
    this.x = x + canvas.width / 2;
    this.y = -y + canvas.height / 2;
    this.size = Math.random() * 4 + 1;
    this.color = color;
    this.alpha = 1;
    this.velocity = {
      x: (Math.random() - 0.5) * 1.5,
      y: (Math.random() - 0.5) * 1.5,
    };
  }

  update() {
    this.x += this.velocity.x;
    this.y += this.velocity.y;
    this.alpha -= 0.005;
  }

  draw() {
    ctx.save();
    ctx.globalAlpha = this.alpha;
    ctx.fillStyle = this.color;
    ctx.shadowColor = this.color;
    ctx.shadowBlur = 10;
    ctx.beginPath();
    ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
    ctx.fill();
    ctx.restore();
  }
}

function animate() {
  requestAnimationFrame(animate);
  ctx.fillStyle = "rgba(0, 0, 0, 0.1)";
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  let t = Math.random() * Math.PI * 2;
  let { x, y } = heartFunction(t);
  const color =
    romanticColors[Math.floor(Math.random() * romanticColors.length)];
  particles.push(new Particle(x * 20, y * 20, color));

  particles.forEach((p, i) => {
    p.update();
    p.draw();
    if (p.alpha <= 0) particles.splice(i, 1);
  });
}

animate();
