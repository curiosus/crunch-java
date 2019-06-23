extends KinematicBody2D

const SPEED = 100
const MAX_SPEED = 200
const FRICTION = 0.1
const BULLET = preload("res://Bullet.tscn")
const HEALTH = 50

var motion = Vector2()
var reload_time = 1.0
var reloading = 0.0
var alive = true
var current_health = HEALTH

func _ready():
    Global.Player = self

func _process(delta):
    reloading -= delta
    update_motion(delta)
    move_and_slide(motion)

func _input(event):
    if event.is_action_pressed("scroll_up"):
        $Camera2D.zoom = $Camera2D.zoom - Vector2(0.1, 0.1)
    elif event.is_action_pressed("scroll_down"):
        $Camera2D.zoom = $Camera2D.zoom + Vector2(0.1, 0.1)
    if event.is_action_pressed("fire"):
        fire(event)

func fire(event):
    if reloading < 0.0:        
        look_at(get_global_mouse_position())
        var rot = rotation
        var bullet = BULLET.instance()
        var fire_position = Vector2($Gun.global_position.x, $Gun.global_position.y)
        bullet.position = $Gun.position
        bullet.start(fire_position, rot)
        get_parent().add_child(bullet)
        reloading = reload_time        

func update_motion(delta):
    look_at(get_global_mouse_position())
    if Input.is_action_pressed("ui_right"):
        motion.x = clamp((motion.x + SPEED), 0, MAX_SPEED)
    elif Input.is_action_pressed("ui_left"):
        motion.x = clamp((motion.x - SPEED), -MAX_SPEED, 0)
    else:
        motion.x = lerp(motion.x, 0, FRICTION)

    if Input.is_action_pressed("ui_up"):
        motion.y = clamp((motion.y - SPEED), -MAX_SPEED, 0)
    elif Input.is_action_pressed("ui_down"):
        motion.y = clamp((motion.y + SPEED), 0, MAX_SPEED)
    else:
        motion.y = lerp(motion.y, 0, FRICTION)

func hit(damage):
    current_health = current_health - damage
    if current_health <= 0:
        visible = false
        alive = false
        if $Timer.is_stopped():
            $Timer.start()


func respawn():
    current_health = HEALTH
    visible = true
    alive = true
       

func _on_Timer_timeout():
    respawn()
