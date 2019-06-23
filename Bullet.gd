extends KinematicBody2D

var screen_size
var velocity = Vector2(500, 0)
var speed = 1000
var damage = 2

func _ready():
    screen_size = get_viewport_rect().size
    Global.bullet = self

func start(pos, dir):
    rotation = dir
    position = pos
    velocity = Vector2(speed, 0).rotated(rotation)

func _physics_process(delta):
    var collision = move_and_collide(velocity * delta)
    if collision &&  collision.collider.has_method("hit"):
        collision.collider.hit(damage)
        queue_free()




