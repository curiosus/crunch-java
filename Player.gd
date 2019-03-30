extends Area2D

export var speed = 400
var screen_size

const BULLET = preload("res://Bullet.tscn")

onready var raycast = get_node("RayCast2D")

func _ready():
	screen_size = get_viewport_rect().size
	yield(get_tree(), "idle_frame")
	get_tree().call_group("enemies", "set_player", self)
	
	
	
func _process(delta):
	var velocity = Vector2()
	
	
	
	if Input.is_action_pressed("ui_right"):
		velocity.x += speed
	if Input.is_action_pressed("ui_left"):
		velocity.x -= speed
	if Input.is_action_pressed("ui_down"):
		velocity.y += speed
	if Input.is_action_pressed("ui_up"):
		velocity.y -= speed



	
	#var r = randi()%4+1
#	if r == 1:
#		velocity.x += speed * 20
#	if r == 2:
#		velocity.x -= speed * 20
#	if r == 3:
#		velocity.y += speed * 20 
#	if r == 4:
#		velocity.y -= speed * 20

	
		
	if velocity.length() > 0:
		velocity = velocity.normalized() * speed
		$AnimatedSprite.play()
	else:
		$AnimatedSprite.stop()
		
	position += velocity * delta
	position.x = clamp(position.x, 0, screen_size.x)
	position.y = clamp(position.y, 0, screen_size.y)
	
	if velocity.x != 0:
		$AnimatedSprite.animation = "right"
		$AnimatedSprite.flip_v = false
		$AnimatedSprite.flip_h = velocity.x < 0
	elif velocity.y != 0:
		$AnimatedSprite.animation = "up"
#		$AnimatedSprite.flip_v = velocity.y > 0

	var look_vector = get_global_mouse_position() - global_position
	global_rotation = atan2(look_vector.y, look_vector.x)

	if Input.is_action_just_pressed("shoot"):
		fire()
		var coll = raycast.get_collider()		
		if raycast.is_colliding() && coll.has_method("kill"):
			coll.kill()
			
	
func kill():
	print("Player killed")
	get_tree().reload_current_scene()

func fire():
	var bullet = BULLET.instance()
	bullet.global_position = global_position
	get_parent().add_child(bullet)
