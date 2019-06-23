extends KinematicBody2D

const SPEED = 5
const MAX_SPEED = 200
const Friction = 0.1
const BULLET = preload("res://Bullet.tscn")
const MAX_DETECTION_RANGE = 800

var motion = Vector2()
var possible_destinations = []
var path = []
var destination = Vector2()
var navigation_stop_threshold = 5
var reload_time = 1.0
var reloading = 0.0

onready var navigation= Global.navigation
onready var available_destinations = Global.destinations
onready var player = Global.Player

func _ready():
    add_to_group("npc")
    possible_destinations = available_destinations.get_children()
    make_path()

func _process(delta):
    reloading -= delta
    navigate()
    fire()
    
func navigate():
    if path.size() > 0:
        var distance_to_destination = position.distance_to(path[0])
        destination = path[0]
        if distance_to_destination > navigation_stop_threshold:
            move()
        else:
            update_path()
    else:
        make_path()

func update_path():
    if path.size() == 1:
        if $Timer.is_stopped():
            $Timer.start()
    else:
        path.remove(0)
        

func move():
    look_at(destination)
    motion = (destination - position).normalized() * MAX_SPEED
    if is_on_wall():
        make_path()
    move_and_slide(motion)

func make_path():
    randomize()
    var next_destination = possible_destinations[randi() % possible_destinations.size()]
    path = navigation.get_simple_path(global_position, next_destination.global_position, false)
    
func fire():
    if reloading < 0.0 and player.alive:
        look_at(player.position)
        if is_target_los():
            var bullet = BULLET.instance()
            var fire_position = Vector2($Gun.global_position.x, $Gun.global_position.y)
            bullet.position = $Gun.position
            var rot = rotation
            bullet.start(fire_position, rot)
            get_parent().add_child(bullet)
            reloading = reload_time

func is_target_los():
    var space = get_world_2d().direct_space_state
    var target = space.intersect_ray(global_position, player.global_position, [self])
    var distance_to_target = player.global_position.distance_to(global_position)
    return distance_to_target < MAX_DETECTION_RANGE
    
        

func hit(damage):
    queue_free()


func _on_Timer_timeout():
    make_path()
