extends Area2D

var in_range = false

func _ready():
    Global.door = self



func _on_Door_body_entered(body):
    if not body == Global.Player and not $AnimationPlayer.is_playing():
        open()
    else:
        in_range = true
        
func _input(event):
    if event.is_action_pressed("Open_Door") and in_range:
        open()
        
        
    


func _on_Door_body_exited(body):
    if body == Global.Player:
        in_range = false

func open():
    $AnimationPlayer.play("open")