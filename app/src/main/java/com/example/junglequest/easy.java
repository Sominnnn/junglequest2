package com.example.junglequest;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class easy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_easy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        // Get the draggable animal ImageView
        ImageView draggableAnimal = findViewById(R.id.drag_lion);

        // Set long click listener for drag operation
                draggableAnimal.setOnLongClickListener(v -> {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE); // Optional: hide the original view during drag
                    return true;
                });

        // Get the container layout - main is a ConstraintLayout, not an ImageView
                ConstraintLayout dropZone = findViewById(R.id.main);

        // Set drag listener on the container layout
                dropZone.setOnDragListener((v, event) -> {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            return true;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            return true;
                        case DragEvent.ACTION_DRAG_LOCATION:
                            return true;
                        case DragEvent.ACTION_DRAG_EXITED:
                            return true;
                        case DragEvent.ACTION_DROP:
                            // Get the dragged view
                            View draggedView = (View) event.getLocalState();

                            // Calculate position adjusted for the view's width/height
                            float dropX = event.getX() - (draggedView.getWidth() / 2);
                            float dropY = event.getY() - (draggedView.getHeight() / 2);

                            // Set the new position
                            draggedView.setX(dropX);
                            draggedView.setY(dropY);

                            // Make the view visible again
                            draggedView.setVisibility(View.VISIBLE);
                            return true;
                        case DragEvent.ACTION_DRAG_ENDED:
                            // Ensure the view is visible if the drag ended without a drop
                            View view = (View) event.getLocalState();
                            if (view.getVisibility() == View.INVISIBLE) {
                                view.setVisibility(View.VISIBLE);
                            }
                            return true;
                        default:
                            return false;
                    }
                });
    }
}