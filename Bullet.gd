extends Sprite



const VELOCITY	= Vector2(500, 0)
var screen_size

func _ready():
	screen_size = get_viewport_rect().size
	
func _process(delta):
	move(delta)
	remove_when_off_screen()
	
	
func move(delta):
	global_position += VELOCITY * delta
	
func remove_when_off_screen():
	if global_position.x > screen_size.x:
		queue_free()
