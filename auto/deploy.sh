export VERSION=$(< version)

SHIPPER="scl enable rh-ruby23 -- $HOME/working/rea-shipper/bin/rea-shipper"
$SHIPPER ship --override var.app_version=$VERSION && echo "Shipped version $VERSION" || echo "Failed to ship $VERSION"

