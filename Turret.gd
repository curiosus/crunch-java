extends KinematicBody2D

const SHELL = preload("res://Shell.tscn")
const FOV_TOLERANCE = PI / 12
const ROTATE_TIME = .125/2.0
var rotate_time = ROTATE_TIME
var rotate_speed = 2.5
var detection_color = Color(200,50,100)
var default_color = Color(255, 0, 0)
var max_detection_range = 750

var reload_time = 1.0
var reloading = 0.0


onready var Player = Global.Player

func _process(delta):
    reloading = reloading - delta
    if in_fov_tolerance() and in_line_of_sight():
        fire()
    else:
        scan_rotation(delta)
        
func fire():
    look_at(Player.global_position)
    if reloading < 0.0:
        var shell_1 = SHELL.instance()
        var shell_2 = SHELL.instance()
        var pos1 = $CannonLeft.global_position
        var pos2 = $CannonRight.global_position
        shell_1.start(pos1, rotation)
        shell_2.start(pos2, rotation)
        get_parent().add_child(shell_1)
        get_parent().add_child(shell_2)
        reloading = reload_time
        
        
func scan_rotation(delta):
    rotate_time = rotate_time - delta
    if rotate_time < 0:
        rotate(PI / 8 * rotate_speed * delta)
        rotate_time = ROTATE_TIME    


func in_fov_tolerance():
    var facing_direction = Vector2(1, 0).rotated(global_rotation)
    var direction_to_target = (Player.position - global_position).normalized()
    return abs(direction_to_target.angle_to(facing_direction)) < FOV_TOLERANCE 
    
func in_line_of_sight():
    var space = get_world_2d().direct_space_state
    var obstacle = space.intersect_ray(global_position, Player.global_position, [self])
    var distance_to_target = Player.global_position.distance_to(global_position)
    var target_in_range = distance_to_target < max_detection_range
    return obstacle.collider == Player and target_in_range
        