extends KinematicBody2D

const MOVE_SPEED = 100

onready var raycast = get_node("RayCast2D")

var player = null


func _ready():
	add_to_group("enemies")
	
func _physics_process(delta):
	if player == null:
		return
		
	var vector_to_player = player.global_position - global_position
	vector_to_player = vector_to_player.normalized()
	global_rotation = atan2(vector_to_player.y, vector_to_player.x)
	move_and_collide(vector_to_player * MOVE_SPEED * delta)
	
	if raycast.is_colliding():
		var coll = raycast.get_collider()
		if coll.name == "Player":
			coll.kill()

func kill():
	print("Enemy killed")
	queue_free()
	
func set_player(p):
	player = p
	
