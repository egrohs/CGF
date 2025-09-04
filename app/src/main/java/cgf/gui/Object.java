package cgf.gui;
import java.util.List;
import java.util.Vector;

//@Data
//TODO jackson para serialização do arquivo json
public class Object {
    private Vector alt_view_angle; // When non-zero, the Alt view will use the specified Euler angle to look at the
                            // object.; //
    private float angular_drag; // Angular drag. Unity rigidbody property.; //
    private boolean auto_raise; // If the object should be lifted above other objects to avoid collision when
                                // held by a player.; //
    private float  bounciness; // Bounciness, value of 0-1. Unity physics material.; //
    private float drag; // Drag. Unity rigidbody property.; //
    private boolean drag_selectable; // When false, the object will not be selected by regular (click and drag)
                                     // selection boxes that are drawn around the object.
    // Players may proceed to override this behavior by holding the "Shift" modifier
    // whilst drag selecting.
    private float dynamic_friction; // Dynamic friction, value of 0-1. Unity physics material.; //
    private boolean gizmo_selectable; // When false, the object cannot be selected with the Gizmo tool.
    private boolean grid_projection; // If grid lines can appear on the Object if visible grids are turned on.; //
    
    private Vector held_position_offset; // Position offset from pointer.; //
    private boolean held_reduce_force; // When the Object collides with something while moving this is automatically
                                       // enabled and reduces the movement force.; //
    private Vector held_rotation_offset; // Rotation offset from pointer.; //
    
    private boolean interactable; // If the object can be interacted with by Players. Other object will still be
                                  // able to interact with it.; //
    
    private boolean loading_custom; // If the Object's custom elements (images/models/etc) are loading. Read only.;
    
    private float mass; // Mass. Unity rigidbody property.; //
    private int max_typed_number; // Determines the maximum number of digits which a user may type whilst hovering
                              // over the object.
    // If typing another digit would exceed the value assigned here, the
    // corresponding behavior (e.g. onObjectNumberTyped/onNumberTyped) is triggered
    // immediately, improving responsiveness.
    private boolean measure_movement; // Measure Tool will automatically be used when moving the Object.; //
    
    private Vector pick_up_position; // The position the Object was picked up at. Read only.; //
    private Vector pick_up_rotation; // The rotation the Object was picked up at. Read only.; //
    private float remainder; // If this object is a container that cannot exist with less than two contained
                       // objects (e.g. a deck), taking out the second last contained object will
                       // result in the container being destroyed. In its place the last remaining
                       // object in the container will be spawned.
    // This variable provides a reference to the remaining object when it is being
    // spawned. Otherwise, it's nil. Read only.
    private boolean resting; // If the Object is at rest. Unity rigidbody property.; //
    private String script_code; // The Lua Script on the Object.; //
    private String script_state; // The saved data on the object. See onSave().; //
    private boolean spawning; // If the Object is finished spawning. Read only.; //
    private float static_friction; // Static friction, value of 0-1. Unity physics material.; //
    private boolean sticky; // If other Objects on top of this one are also picked up when this Object is.;
@Deprecated
private String tag; // Use type. This object's type. Read only.; //
private boolean tooltip; // If the tooltip opens when a pointer hovers over the object. Tooltips display
                             // name and description.; //
    
    private boolean use_gravity; // If gravity affects this object.; //
    private boolean use_grid; // If snapping to grid is enabled or not.; //
    
    private boolean use_rotation_value_flip; // Switches the axis the Object rotates around when flipped.; //
    private boolean use_snap_points; // If snap points are used or ignored.; //
    
    @Deprecated
    private int value_flags; // Use object tags.
    // A bit field. When objects with overlapping value_flags are selected and
    // hovered over, their values will be summed together.; //

public boolean setPosition(Vector pos);
public boolean setPositionSmooth(Vector pos, boolean collide, boolean fast);
public boolean setRotation(rot);

//createButton(...)

}